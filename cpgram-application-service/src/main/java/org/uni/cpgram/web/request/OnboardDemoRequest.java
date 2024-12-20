package org.uni.cpgram.web.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.uni.cpgram.persistence.dto.OnboardDemoDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardDemoRequest {
 private String message;
 private OnboardDemoDTO onboardDemoDTO;

}
