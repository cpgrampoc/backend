package org.uni.cpgram.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.uni.cpgram.persistence.dto.OnboardDemoDTO;
import org.uni.cpgram.web.request.GrievanceRequest;
import org.uni.cpgram.web.request.OnboardDemoRequest;
import org.uni.cpgram.web.response.GrievanceResponse;
import org.uni.cpgram.web.response.OnboardDemoResponse;
import org.uni.cpgram.web.service.OnboardDemoService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/onboard")
public class OnboardDemoController {

    @Autowired
    private OnboardDemoService onboardDemoService;

    @PostMapping("/process")
    public ResponseEntity<OnboardDemoResponse> onboard(@RequestBody OnboardDemoRequest onboardDemoRequest) throws Exception {

            OnboardDemoResponse onboardDemoResponse = onboardDemoService.create(onboardDemoRequest);
            return new ResponseEntity<>(onboardDemoResponse, HttpStatus.CREATED);

    }

    @PostMapping("/search")
    public ResponseEntity<OnboardDemoResponse> getAllDetails(@RequestBody OnboardDemoRequest onboardDemoRequest) {

            OnboardDemoResponse allDetails = onboardDemoService.getAllDetails(onboardDemoRequest.getOnboardDemoDTO().getParent());
            return new ResponseEntity<>(allDetails, HttpStatus.CREATED);





    }





}

