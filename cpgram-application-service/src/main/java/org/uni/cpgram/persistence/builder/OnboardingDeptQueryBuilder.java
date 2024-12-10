package org.uni.cpgram.persistence.builder;

public class OnboardingDeptQueryBuilder {


    public static String getCreateQuery() {

        return  "INSERT INTO m_onboarding_dept (uuid, dept_code, dept_name, field_name, level, parent_level, is_mandatory, owner_name, field_details) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        return "INSERT INTO m_onboarding_dept (uuid, dept_code, dept_name, field_name, level, parent_level, is_mandatory, owner_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public static String getUpdateQuery() {
        return "UPDATE m_onboarding_dept SET ";
    }

    public static String getSearchQuery() {
        return "SELECT * FROM m_onboarding_dept WHERE 1=1 ";
    }
}
