package org.uni.cpgram.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.uni.cpgram.web.request.GrievanceRequest;
import org.uni.cpgram.web.request.SearchCriteria;
import org.uni.cpgram.web.response.GrievanceResponse;
import org.uni.cpgram.web.service.GrievanceService;

import java.util.Collection;

@RestController
@RequestMapping("/grievance")
public class GrievanceController {

    @Autowired
    private GrievanceService grievanceService; // Service to handle business logic


    @PostMapping("/test")
    public String testDemo(){
        return "working fine";
    }

    @PostMapping("/create")
    public ResponseEntity<GrievanceResponse> createGrievance(@RequestBody GrievanceRequest grievanceRequest) {
        try {
            GrievanceResponse grievanceResponse = grievanceService.createGrievance(grievanceRequest.getGrievanceDTO());
            return new ResponseEntity<>(grievanceResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            // Optionally, you can return an error-specific response here
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/search")
    public ResponseEntity<GrievanceResponse> searchGrievanceDetails(@ModelAttribute SearchCriteria searchCriteria) {
        try {

            GrievanceResponse grievanceResponse = grievanceService.getGrievanceById(searchCriteria);
            if (grievanceResponse == null || CollectionUtils.isEmpty(grievanceResponse.getGrievanceDTO())) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // If the response is valid, return it with HttpStatus.CREATED
            return new ResponseEntity<>(grievanceResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
