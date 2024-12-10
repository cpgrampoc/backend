package org.uni.cpgram.persistence.dao;

import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;

import java.sql.SQLException;
import java.util.List;

public interface OnboardingDeptDao {
    public String create(OnboardingDept onboardingDept);
    Boolean update(OnboardingDept onboardingDept) throws SQLException;
    List<OnboardingDept> search(OnboardingDept onboardingDept);
}
