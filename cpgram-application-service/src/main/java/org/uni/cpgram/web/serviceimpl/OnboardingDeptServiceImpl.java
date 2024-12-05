package org.uni.cpgram.web.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uni.cpgram.model.OnboardingDept;
import org.uni.cpgram.model.mapper.OnboardingDeptModelMapper;
import org.uni.cpgram.persistence.dao.OnboardingDeptDao;
import org.uni.cpgram.persistence.dto.OnboardingDeptDTO;
import org.uni.cpgram.web.service.OnboardingDeptService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OnboardingDeptServiceImpl implements OnboardingDeptService {

    @Autowired
    private OnboardingDeptDao onboardingDeptDao;

    @Autowired
    private OnboardingDeptModelMapper  onboardingDeptModelMapper;


    @Override
    public void save(OnboardingDeptDTO onboardingDeptDTO) {
        try {
            OnboardingDept onboardingDept = onboardingDeptModelMapper.toModel(onboardingDeptDTO);
            if (onboardingDept.getUuid() == null || onboardingDept.getUuid().isEmpty()) {
               String uuid = onboardingDeptDao.create(onboardingDept);
               if(!StringUtils.isEmpty(uuid)){
                     onboardingDeptDTO.setUuid(uuid);
                } else {
                     throw new RuntimeException("Failed to save");
               }
            } else {
                Boolean isUpdated = onboardingDeptDao.update(onboardingDept);
                if (!isUpdated) {
                    throw new RuntimeException("Failed to update");
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while saving the onboarding department", e);
            throw new RuntimeException("An error occurred while saving the onboarding department", e);
        }
    }

    @Override
    public List<OnboardingDeptDTO> search(OnboardingDeptDTO onboardingDept) {
        try {
            List<OnboardingDept> onboardingDepts = onboardingDeptDao.search(onboardingDeptModelMapper.toModel(onboardingDept));
            return onboardingDepts.stream().map(onboardingDeptModelMapper::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("An error occurred while searching for onboarding departments", e);
            throw new RuntimeException("An error occurred while searching for onboarding departments", e);
        }
    }
}
