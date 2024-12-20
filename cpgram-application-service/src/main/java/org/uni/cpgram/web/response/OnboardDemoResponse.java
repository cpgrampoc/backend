package org.uni.cpgram.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.uni.cpgram.model.OnboardDemo;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardDemoResponse {
    private List<String> msg;
    private List<OnboardDemoDTO> onboardDemoDTOList;
}
