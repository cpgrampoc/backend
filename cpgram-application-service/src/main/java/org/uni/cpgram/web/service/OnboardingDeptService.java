package org.uni.cpgram.web.service;

import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;

import java.util.List;

public interface OnboardingDeptService {
    public void save(OnboardingDeptDTO onboardingDeptDTO);
    List<OnboardingDeptDTO> search(OnboardingDeptDTO onboardingDept);
}