package org.uni.cpgram.persistence.dao;

import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;

import java.util.List;

public interface OnboardingDeptDao {
    public String create(OnboardingDept onboardingDept);
    Boolean update(OnboardingDept onboardingDept);
    List<OnboardingDept> search(OnboardingDept onboardingDept);
}
