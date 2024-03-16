package com.example.NaquelesDias.infrastructure.controlers;

import com.example.NaquelesDias.model.HealthCenter.HealthCenterRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.NaquelesDias.model.user.AdressInformation.AddressInfoRepository;
import com.example.NaquelesDias.model.user.BiologicalInformation.AddressInformation;
import com.example.NaquelesDias.model.user.User;
import com.example.NaquelesDias.model.user.UserRepository;
import com.example.NaquelesDias.service.HealthCenterService;
import com.example.NaquelesDias.service.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/healthCenter")
public class HealthCenterController {

    private final Logger logger = LoggerFactory.getLogger(BloodCentersController.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressInfoRepository addressInfoRepository;
    @Autowired
    private HealthCenterService healthCenterService;
    @Autowired
    private HealthCenterRepository healthCenterRepository;

    // GET ALL HC
    @GetMapping("/list")
    public ResponseEntity<String> getHealthCenterList(@NonNull HttpServletRequest request) {
        logger.info("-Starting HealthCenters List Getter-");

        AddressInformation addressInformation = getUserAddressInfo(request);
        String result = healthCenterService.getHealthCenterList(addressInformation);

        logger.info("-Success Getting HealthCenters List-");

        return ResponseEntity.ok(result);
    }

    //TODO: Fix this because I think is not working properly

    //GET ALL DETAILS FROM ONE HC
    @GetMapping("/detailedList")
    public ResponseEntity<String> getDetailedHealthCenterList(@NonNull HttpServletRequest request) {
        logger.info("-Starting DetailedHealthCenters List Getter-");

        String result = healthCenterService.getDetailedHealthCenterList();

        logger.info("-Success Getting DetailedHealthCenters List-");

        return ResponseEntity.ok(result);
    }

    // GET ALL DOCTORS WORKING IN A HC
    @GetMapping("/doctorsList")
    public ResponseEntity<String> getDoctorsHealthCenterList(@RequestParam("healthCenterId") int healthCenterId) {
        logger.info("-Starting Doctors Health Center List Getter-");

        String result = healthCenterService.getDoctorsHealthCenterList(healthCenterId);

        logger.info("-Success Getting Doctors Health Center List-");

        return ResponseEntity.ok(result);
    }

    // GET ALL CAMPAIGNS FROM A HC
    @GetMapping("/campaignsList")
    public ResponseEntity<String> getCampaignsHealthCenterList(@RequestParam("healthCenterId") int healthCenterId) {
        logger.info("-Starting Campaigns Health Center List Getter-");

        String result = healthCenterService.getCampaignsHealthCenterList(healthCenterId);

        logger.info("-Success Getting Campaigns Health Center List-");

        return ResponseEntity.ok(result);
    }


    public AddressInformation getUserAddressInfo(@NonNull HttpServletRequest request) {
        String token = tokenService.recoverToken(request);

        String userEmail = tokenService.getEmailFromToken(token);
        logger.info("User Email: {}", userEmail);

        User user = (User) userRepository.findByEmail(userEmail);
        int addressInfoId = user.getAddressInfoId();
        logger.info("User Id: {}", addressInfoId);

        return addressInfoRepository.findById(addressInfoId);
    }



}