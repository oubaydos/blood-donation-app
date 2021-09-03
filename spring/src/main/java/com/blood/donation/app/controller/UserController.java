package com.blood.donation.app.controller;

import com.blood.donation.app.model.Donor;
import com.blood.donation.app.service.DonorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
public class USerController {
    private static final Logger LOG = LoggerFactory.getLogger(USerController.class);
    private final DonorService donorService;

    public USerController(DonorService donorService) {
        this.donorService = donorService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/", params = {"Action=SignUpDonor"})
    public ResponseEntity<String> signUpDonor(@Valid Donor donor) {

        LOG.debug("signUpDonor request: {}", donor);
        this.donorService.signUpDonor(donor);
        LOG.debug("signUpDonor response");

        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(200);
        return bodyBuilder.body("successfully added the donor");
        //return the jwt
    }
}
