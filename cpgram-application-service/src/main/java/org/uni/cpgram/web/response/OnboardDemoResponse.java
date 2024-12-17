package org.uni.cpgram.web.response;

import lombok.Builder;
import lombok.Data;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;

import java.util.List;

@Data
@Builder
public class OnboardDemoResponse {
    List<OnboardDemoDTO> onboardDemoDTOList;
}
