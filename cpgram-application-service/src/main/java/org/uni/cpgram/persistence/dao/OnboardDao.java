package org.uni.cpgram.persistence.dao;

import org.uni.cpgram.model.OnboardDemo;

import java.util.List;



public interface OnboardDao  {

    boolean existsByDescription(String description);
    List<OnboardDemo> findByParent(String parent,Long Value);
    List<OnboardDemo> findByDescription( String searchCriteria,String value);
    List<OnboardDemo> saveAll(List<OnboardDemo> onboardDemoList);
    long getNextUserId();

}
