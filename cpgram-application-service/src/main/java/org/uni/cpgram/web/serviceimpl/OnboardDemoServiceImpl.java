package org.uni.cpgram.web.serviceimpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.model.mapper.OnboardDemoModelMapper;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;
import org.uni.cpgram.repository.OnboardDemoRepository;
import org.uni.cpgram.web.request.OnboardDemoRequest;
import org.uni.cpgram.web.response.OnboardDemoResponse;
import org.uni.cpgram.web.service.OnboardDemoService;

import java.util.*;

@Service
public class OnboardDemoServiceImpl implements OnboardDemoService {


    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/embeddings";


    @Autowired
    private OnboardDemoRepository onboardDemoRepository;

    @Autowired
    private OnboardDemoModelMapper onboardDemoModelMapper;
    @Override
    public OnboardDemoResponse create(OnboardDemoRequest onboardDemoRequest) {
        List<OnboardDemoDTO> allOnboardData = new ArrayList<>();
        onboardDemoRequest.getOnboardDemoDTO().setId(UUID.randomUUID().toString());

        // Check if the parent description already exists
        if (onboardDemoRepository.existsByDescription(onboardDemoRequest.getOnboardDemoDTO().getDescription())) {
            // If parent exists, fetch its ID

            Optional<OnboardDemo> existingParentId = onboardDemoRepository.findIdByDescription(onboardDemoRequest.getOnboardDemoDTO().getDescription());
            existingParentId.ifPresent(onboardDemo ->{

                onboardDemoRequest.getOnboardDemoDTO().setId(onboardDemo.getId());
                onboardDemoRequest.getOnboardDemoDTO().setEmbedding(onboardDemo.getEmbedding());

            });
        } else {
            // If parent does not exist, generate a new ID and add it to the list
            onboardDemoRequest.getOnboardDemoDTO().setId(UUID.randomUUID().toString());
            List<Double> embeddingList = fetchEmbedding(onboardDemoRequest.getOnboardDemoDTO().getDescription());
            onboardDemoRequest.getOnboardDemoDTO().setEmbedding(embeddingList);
            allOnboardData.add(onboardDemoRequest.getOnboardDemoDTO());
        }

        // Extract data from the parent level
        extractCategoriesAndSubCategories(onboardDemoRequest.getOnboardDemoDTO(), allOnboardData, null);
        List<OnboardDemo> onboardDemoList=allOnboardData.stream().map(onboardDemoModelMapper::toModel).toList();
        // Save all the extracted data in one database call
        List<OnboardDemo> onboardDemo= onboardDemoRepository.saveAll(onboardDemoList);
        List<OnboardDemoDTO> onboardDemoDTORes=onboardDemo.stream().map(onboardDemoModelMapper::toDTO).toList();
        return OnboardDemoResponse.builder().onboardDemoDTOList(onboardDemoDTORes).build();
    }


    @Override
    public OnboardDemoResponse getAllDetails(String parentId) {
        List<OnboardDemo> onboardDemoList= onboardDemoRepository.findAllByParent(parentId);
        List<OnboardDemoDTO> onboardDemoDTOList=onboardDemoList.stream().map(onboardDemoModelMapper::toDTO).toList();
        return OnboardDemoResponse.builder().onboardDemoDTOList(onboardDemoDTOList).build();
    }


    // Recursive method to extract categories and subCategories, and add to the list
    private void extractCategoriesAndSubCategories(OnboardDemoDTO onboardDemoDTO,
                                                   List<OnboardDemoDTO> allOnboardData,
                                                   String parentId) {


            // If the parentId is not null, set it as the parent ID for the current DTO
            if (parentId != null) {
                onboardDemoDTO.setParent(parentId);
            }

            // Add the current DTO (including its categories and subcategories) to the list
            allOnboardData.add(onboardDemoDTO);


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
                extractCategoriesAndSubCategories(category, allOnboardData, onboardDemoDTO.getId());
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
                extractCategoriesAndSubCategories(subCategory, allOnboardData, onboardDemoDTO.getId());
            }
        }
    }

    private List<Double> fetchEmbedding(String description) {
        RestTemplate restTemplate = new RestTemplate();

        // Create the request body for OpenAI embeddings API
        String jsonBody = "{"
                + "\"model\": \"text-embedding-ada-002\","
                + "\"input\": \"" + description + "\""
                + "}";

        // Set the headers for authorization
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
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
