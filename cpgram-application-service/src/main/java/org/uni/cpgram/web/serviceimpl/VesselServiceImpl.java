package org.uni.cpgram.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uni.cpgram.repository.VesselRepository;
import org.uni.cpgram.web.service.VesselService;

import java.util.List;
import java.util.Map;

@Service
public class VesselServiceImpl implements VesselService {
    @Autowired
    private VesselRepository vesselRepository;

    public List<Map<String, Object>> getVesselDetails() {
        return vesselRepository.getVesselDetails();
    }
}
