package org.uni.cpgram.web.service;

import org.uni.cpgram.persistence.dto.OnboardDemoDTO;
import org.uni.cpgram.web.request.OnboardDemoRequest;
import org.uni.cpgram.web.response.OnboardDemoResponse;

import java.util.List;
import java.util.Set;

public interface OnboardDemoService {

    OnboardDemoResponse create(OnboardDemoRequest onboardDemoRequest);
    OnboardDemoResponse getAllDetails(String parentId);
}
