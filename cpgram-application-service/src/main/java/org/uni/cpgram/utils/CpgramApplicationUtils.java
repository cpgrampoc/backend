package org.uni.cpgram.utils;

import digit.models.coremodels.AuditDetails;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.stereotype.Component;

@Component
public class CpgramApplicationUtils {

    public AuditDetails buildCreateAuditDetails() {

        //it will change it later by request info username
        // like String uuid = requestInfo.getUserInfo().getUuid();
        String uuid = "ADMIN";
        return AuditDetails.builder().createdBy(uuid).createdTime(System.currentTimeMillis()).lastModifiedBy(uuid)
                .lastModifiedTime(System.currentTimeMillis()).build();
    }

}
