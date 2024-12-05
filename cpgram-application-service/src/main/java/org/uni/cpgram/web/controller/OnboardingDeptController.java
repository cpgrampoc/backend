package org.uni.cpgram.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uni.cpgram.constant.CpgramConstants;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;
import org.uni.cpgram.web.response.OnboardingDeptResponse;
import org.uni.cpgram.web.service.OnboardingDeptService;

import java.util.List;

@RestController
@RequestMapping("/onboarding-dept")
@Slf4j
public class OnboardingDeptController {

    @Autowired
    private OnboardingDeptService onboardingDeptService;

    @PostMapping("/create")
    public ResponseEntity<OnboardingDeptResponse> save(@RequestBody OnboardingDeptDTO onboardingDeptDTO) {
        OnboardingDeptResponse response = new OnboardingDeptResponse();
        try {
            onboardingDeptService.save(onboardingDeptDTO);
            response.setStatus(CpgramConstants.STATUS_CODE_200);
            response.setMessage(CpgramConstants.ONBOARDING_SUCCESS_MSG);
            response.setOnboardingDept(List.of(onboardingDeptDTO));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while saving the onboarding department", e);
            response.setStatus(CpgramConstants.STATUS_CODE_500);
            response.setMessage(CpgramConstants.ONBOARDING_ERROR_MSG);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<OnboardingDeptResponse> search(@RequestBody OnboardingDeptDTO onboardingDeptDTO) {
        OnboardingDeptResponse response = new OnboardingDeptResponse();
        try {
            List<OnboardingDeptDTO> result = onboardingDeptService.search(onboardingDeptDTO);
            response.setStatus(CpgramConstants.STATUS_CODE_200);
            response.setMessage(CpgramConstants.DEPT_SEARCH_SUCCESS);
            response.setOnboardingDept(result.isEmpty() ? null : result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while searching for onboarding departments", e);
            response.setStatus(CpgramConstants.STATUS_CODE_500);
            response.setMessage(CpgramConstants.DEPT_SEARCH_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
