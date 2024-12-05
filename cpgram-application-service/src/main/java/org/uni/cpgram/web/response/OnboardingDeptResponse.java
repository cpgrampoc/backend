package org.uni.cpgram.web.response;

import lombok.Data;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;

import java.util.List;

@Data
public class OnboardingDeptResponse {

    private String status;
    private String message;
    private List<OnboardingDeptDTO> onboardingDept;
}
