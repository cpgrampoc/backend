package org.uni.cpgram.web.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class SearchCriteria {
    private Set<String> grievanceId;

}
