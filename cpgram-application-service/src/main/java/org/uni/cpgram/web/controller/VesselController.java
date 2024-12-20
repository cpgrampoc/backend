package org.uni.cpgram.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uni.cpgram.web.service.VesselService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vessel")
public class VesselController {

    @Autowired
    private VesselService vesselService;

    @PostMapping("/details")
    public List<Map<String, Object>> getVesselDetails() {
        // This will return a list of maps, which will be serialized as JSON
        return vesselService.getVesselDetails();
    }
}

