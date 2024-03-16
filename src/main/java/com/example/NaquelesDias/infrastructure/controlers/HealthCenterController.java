package com.example.NaquelesDias.infrastructure.controlers;

import com.example.NaquelesDias.model.HealthCenter.*;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.NaquelesDias.model.user.AddressInfoRepository;
import com.example.NaquelesDias.model.user.AddressInformation;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CampaignsRepository campaignsRepository;

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

    //POST NEW DOCTOR IN HC
    @PostMapping("/registerNewDoctor")
    public ResponseEntity<String> postNewDoctor(@RequestBody Doctor doctor, @RequestParam("healthCenterId") int healthCenterId) {
        logger.info("-Registering a new Doctor in Health Center-");

        if (!healthCenterRepository.findById(healthCenterId)) {
            logger.error("Health Center with ID {} not found", healthCenterId);
            return ResponseEntity.badRequest().body("Health Center with ID " + healthCenterId + " not found");
        }

        doctor.setHealth_center_id(healthCenterId);

        Doctor savedDoctor = doctorRepository.save(doctor);

        logger.info("-Success Registering a new Doctor in Health Center-");


        return ResponseEntity.ok("New Doctor registered with ID: " + savedDoctor.getId());
    }

    //POST NEW CAMPAIGN IN HC
    @PostMapping("/registerNewCampaign")
    public ResponseEntity<String> postNewDoctor(@RequestBody Campaigns campaign, @RequestParam("healthCenterId") int healthCenterId) {
        logger.info("-Registering a new Campaign in Health Center-");

        if (!healthCenterRepository.findById(healthCenterId)) {
            logger.error("Health Center with ID {} not found", healthCenterId);
            return ResponseEntity.badRequest().body("Health Center with ID " + healthCenterId + " not found");
        }

        campaign.setHealth_center_id(healthCenterId);

        Campaigns savedCampaigns = campaignsRepository.save(campaign);

        logger.info("-Success Registering a new Doctor in Health Center-");


        return ResponseEntity.ok("New Doctor registered with ID: " + savedCampaigns.getId());
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