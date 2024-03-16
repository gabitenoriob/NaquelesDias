package com.example.NaquelesDias.infrastructure.controlers;

import com.example.NaquelesDias.model.HealthCenter.HealthCenterRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.example.NaquelesDias.model.bloodcenter.BloodCenterRepository;
import com.example.NaquelesDias.model.user.AddressInfoRepository;
import com.example.NaquelesDias.model.user.AddressInformation;
import com.example.NaquelesDias.model.user.User;
import com.example.NaquelesDias.model.user.UserRepository;
//import com.example.NaquelesDias.service.BloodCenterService;
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

    @GetMapping("/list")
    public ResponseEntity<String> getHealthCenterList(@NonNull HttpServletRequest request) {
        logger.info("-Starting BloodCenters List Getter-");

        AddressInformation addressInformation = getUserAddressInfo(request);
        String result = healthCenterService.getHealthCenterList(addressInformation);

        logger.info("-Success Getting HealthCenters List-");

        return ResponseEntity.ok(result);
    }

    //TODO: Fix this because I think is not working properly
    @GetMapping("/detailedList")
    public ResponseEntity<String> getDetailedHealthCenterList(@NonNull HttpServletRequest request) {
        logger.info("-Starting DetailedHealthCenters List Getter-");

        String result = healthCenterService.getDetailedHealthCenterList();

        logger.info("-Success Getting DetailedHealthCenters List-");

        return ResponseEntity.ok(result);
    }

    /*@GetMapping("/{bloodCenterId}/bloodstock")
    public ResponseEntity<String> getBloodCenterStock(@PathVariable int bloodCenterId) {
        logger.info("-Starting DetailedBloodCenters List Getter-");

        logger.info("BloodCenterId: {}", bloodCenterId);
        String result = bloodCenterService.getBloodCenterStock(bloodCenterId);

        logger.info("-Success Getting DetailedBloodCenters List-");

        return ResponseEntity.ok(result);
    }*/

    public AddressInformation getUserAddressInfo(@NonNull HttpServletRequest request) {
        String token = tokenService.recoverToken(request);

        String userEmail = tokenService.getEmailFromToken(token);
        logger.info("User Email: {}", userEmail);

        User user = (User) userRepository.findByEmail(userEmail);
        int addressInfoId = user.getAddressInfoId();
        logger.info("User Id: {}", addressInfoId);

        return addressInfoRepository.findById(addressInfoId);
    }

   /* @GetMapping("/mostNeeding")
    public ResponseEntity<String> getMostNeedingBloodCenter(@NonNull HttpServletRequest request) {
        logger.info("-Starting Getter-");

        String token = tokenService.recoverToken(request);
        String userEmail = tokenService.getEmailFromToken(token);
        User user = (User) userRepository.findByEmail(userEmail);

        String rhFactorType = userRepository.getRhFactorType(user.getId());
        String signal = (rhFactorType.equals("1")) ? "-" : "+";
        String userBloodType = userRepository.getBloodType(user.getId(), signal);
        logger.info("User Blood Type: {}", userBloodType);
        String result = bloodCenterRepository.getMostNeedingBloodCenter(userBloodType);
        logger.info("Most Needing Blood Center: {}", result);

        logger.info("-Success Getting Most Needing Blood Center-");

        return ResponseEntity.ok(result);
    }*/
}