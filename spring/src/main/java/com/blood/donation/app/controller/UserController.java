

/*
 * The Software is provided to you by the Licensor under the License, as defined below, subject to the following condition.
 *
 * Without limiting other conditions in the License, the grant of rights under the License will not include, and the License does not grant to you, the right to Sell the Software.
 *
 * For purposes of the foregoing, “Sell” means practicing any or all of the rights granted to you under the License to provide to third parties, for a fee or other consideration (including without limitation fees for hosting or consulting/ support services related to the Software), a product or service whose value derives, entirely or substantially, from the functionality of the Software. Any license notice or attribution required by the License must also include this Commons Clause License Condition notice.
 *
 * Software: blood-donation-app
 *
 * Author: Obaydah BOUIFADENE
 *
 * Year: 2021
 *
 *
 */

package com.blood.donation.app.controller;

import com.blood.donation.app.model.BloodDemand;
import com.blood.donation.app.model.Donor;
import com.blood.donation.app.service.BloodDemandService;
import com.blood.donation.app.service.DonorService;
import com.blood.donation.app.filter.RefreshToken;
import com.blood.donation.app.utils.TESTING;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.blood.donation.app.utils.Utils.refreshTokenPath;


@Validated
@RestController
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final DonorService donorService;
    private final RefreshToken refreshToken;
    private BloodDemandService bloodDemandService;

    @RequestMapping(method = RequestMethod.POST, path = "/signUpDonor")
    public ResponseEntity<String> signUpDonor(@Valid Donor donor) {

        LOG.debug("signUpDonor request: {}", donor.getEmail());
        this.donorService.signUpDonor(donor);
        LOG.debug("signUpDonor response");

        return ResponseEntity.status(HttpStatus.CREATED).body("successfully added the donor");
        //return the jwt
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addBloodRequest")
    public ResponseEntity<String> addBloodRequest(@Valid BloodDemand demand) {

        LOG.debug("addBloodRequest request: {}", demand);
        this.bloodDemandService.addBloodRequest(demand);
        LOG.debug("addBloodRequest response");

        return ResponseEntity.status(HttpStatus.CREATED).body("successfully added the blood request");
    }

    @TESTING
    @RequestMapping(method = RequestMethod.POST, path = "/checkIfUserExists")
    public ResponseEntity<String> checkIfUserExists(@RequestParam("email") String email, @RequestParam("password") String password) {

        LOG.debug("checkIfUserExists request: {}", email);
        String rst = this.donorService.checkIfUserExists(email, password);
        LOG.debug("checkIfUserExists response: {}", rst);

        return ResponseEntity.status(HttpStatus.OK).body(rst);
    }

    @GetMapping(path = refreshTokenPath)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("refreshToken request: {}", request.getHeader("authorization"));
        this.refreshToken.refreshToken(request, response);
        LOG.debug("refreshToken response: {}", response.getStatus());
    }

}
