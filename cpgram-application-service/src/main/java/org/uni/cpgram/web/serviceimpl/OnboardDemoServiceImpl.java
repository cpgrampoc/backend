package org.uni.cpgram.web.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.model.mapper.OnboardDemoModelMapper;
import org.uni.cpgram.persistence.dao.OnboardDao;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;
import org.uni.cpgram.web.request.OnboardDemoRequest;
import org.uni.cpgram.web.response.OnboardDemoResponse;
import org.uni.cpgram.web.service.OnboardDemoService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OnboardDemoServiceImpl implements OnboardDemoService {

    @Autowired
    private OnboardDao onboardDemoRepository;

    private static String getApiKey() throws Exception {
        // Read the JSON file content
        String content = new String(Files.readAllBytes(Paths.get("config/api_keys.json")));

        // Parse the JSON content
        JSONObject json = new JSONObject(content);

        // Retrieve the API key
        return json.getString("openai.api.key");
    }


    private static final String OPENAI_API_URL = "https://api.openai.com/v1/embeddings";




    @Autowired
    private OnboardDemoModelMapper onboardDemoModelMapper;
    @Override
    public OnboardDemoResponse create(OnboardDemoRequest onboardDemoRequest) throws Exception {
        List<OnboardDemoDTO> allOnboardData = new ArrayList<>();
        onboardDemoRequest.getOnboardDemoDTO().setId(UUID.randomUUID().toString());

        // Check if the parent description already exists
        if (onboardDemoRepository.existsByDescription(onboardDemoRequest.getOnboardDemoDTO().getDescription())) {
            // If parent exists, fetch its ID

          String description=onboardDemoRequest.getOnboardDemoDTO().getDescription();
            List<OnboardDemo> existingParentId = onboardDemoRepository.findByParent("description",description);

             boolean isPresent=false;

            if (!CollectionUtils.isEmpty(existingParentId)){
                onboardDemoRequest.getOnboardDemoDTO().setId(existingParentId.get(0).getId());
                onboardDemoRequest.getOnboardDemoDTO().setEmbedding(existingParentId.get(0).getEmbedding());
                isPresent=true;
                extractCategoriesAndSubCategories(isPresent,onboardDemoRequest.getOnboardDemoDTO(), allOnboardData, null);

            }


        } else {
            // If parent does not exist, generate a new ID and add it to the list
            onboardDemoRequest.getOnboardDemoDTO().setId(UUID.randomUUID().toString());
            List<Double> embeddingList = fetchEmbedding(onboardDemoRequest.getOnboardDemoDTO().getDescription());
            onboardDemoRequest.getOnboardDemoDTO().setEmbedding(embeddingList);
           // allOnboardData.add(onboardDemoRequest.getOnboardDemoDTO());
            extractCategoriesAndSubCategories(false,onboardDemoRequest.getOnboardDemoDTO(), allOnboardData, null);
        }

        // Extract data from the parent level

        List<OnboardDemo> onboardDemoList=allOnboardData.stream().map(onboardDemoModelMapper::toModel).toList();
        // Save all the extracted data in one database call
        List<OnboardDemo> onboardDemo= onboardDemoRepository.saveAll(onboardDemoList);
        List<OnboardDemoDTO> onboardDemoDTORes=onboardDemo.stream().map(onboardDemoModelMapper::toDTO).toList();
        return OnboardDemoResponse.builder().onboardDemoDTOList(onboardDemoDTORes).build();
    }


    @Override
    public OnboardDemoResponse getAllDetails(String parentId) {
		  List<OnboardDemo> onboardDemoList=
		 onboardDemoRepository.findByParent("parent",parentId);
		  List<OnboardDemoDTO>
		  onboardDemoDTOList=onboardDemoList.stream().map(onboardDemoModelMapper::toDTO).toList();

          return OnboardDemoResponse.builder().onboardDemoDTOList(onboardDemoDTOList).build();



    }


    // Recursive method to extract categories and subCategories, and add to the list
    private void extractCategoriesAndSubCategories(boolean isPresent,OnboardDemoDTO onboardDemoDTO,
                                                   List<OnboardDemoDTO> allOnboardData,
                                                   String parentId) throws Exception {


            // If the parentId is not null, set it as the parent ID for the current DTO
            if (parentId != null) {
                onboardDemoDTO.setParent(parentId);
            }

            // Add the current DTO (including its categories and subcategories) to the list
           if (!isPresent) {
               allOnboardData.add(onboardDemoDTO);
           }


        // Process categories (if any)
        if (onboardDemoDTO.getCategories() != null) {
            for (OnboardDemoDTO category : onboardDemoDTO.getCategories()) {
                // Generate a new UUID for the category and set its parentId as the current DTO's id
                category.setId(UUID.randomUUID().toString());
                category.setParent(onboardDemoDTO.getId());
                category.setOrgcode(onboardDemoDTO.getOrgcode());
                List<Double> embeddingList  = fetchEmbedding(onboardDemoDTO.getDescription());
                category.setEmbedding(embeddingList);

                // Recursive call for subcategories within the category
                extractCategoriesAndSubCategories(false,category, allOnboardData, onboardDemoDTO.getId());
            }
        }

        // Process subCategories (if any)
        if (onboardDemoDTO.getSubCategories() != null) {
            for (OnboardDemoDTO subCategory : onboardDemoDTO.getSubCategories()) {
                // Generate a new UUID for the subcategory and set its parentId as the current DTO's id
                subCategory.setId(UUID.randomUUID().toString());
                subCategory.setParent(onboardDemoDTO.getId());
                subCategory.setOrgcode(onboardDemoDTO.getOrgcode());
                List<Double> embeddingList = fetchEmbedding(onboardDemoDTO.getDescription());
                subCategory.setEmbedding(embeddingList);

                // Recursive call for subCategories of subCategories (if any)
                extractCategoriesAndSubCategories(false,subCategory, allOnboardData, onboardDemoDTO.getId());
            }
        }
    }

    private List<Double> fetchEmbedding(String description) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Create the request body for OpenAI embeddings API
        String jsonBody = "{"
                + "\"model\": \"text-embedding-ada-002\","
                + "\"input\": \"" + description + "\""
                + "}";

        // Set the headers for authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getApiKey());
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // Send the request to OpenAI API
        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        // Parse the embedding from the response
        return extractEmbeddingFromResponse(response.getBody());
    }

    private List<Double> extractEmbeddingFromResponse(String response) {
        // Extract the embedding array from the response JSON
        // This should be a part of the response that looks like:
        // "data": [{"embedding": [...] }]

        // Use a JSON library (like Jackson) to parse and extract the embedding
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode embeddingNode = rootNode.path("data").get(0).path("embedding");

            // Convert the embedding array to List<Double>
            List<Double> embeddingList = Arrays.asList(objectMapper.readValue(embeddingNode.toString(), Double[].class));

            return embeddingList;
        } catch (Exception e) {
            throw new RuntimeException("Error extracting embedding from OpenAI API response", e);
        }
    }

}
