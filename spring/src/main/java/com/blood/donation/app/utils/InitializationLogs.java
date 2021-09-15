package com.blood.donation.app.utils;

import com.blood.donation.app.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class InitializationLogs {
    /**
     * logs some warnings - debug information about things I haven't handled yet
     */
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @PostConstruct
    void debug(){
        // add a special errorJsonFormat for validation errors
        LOG.error("COM.BLOOD.DONATION.APP.SERVICE.REFRESH_TOKEN IS NOT TESTED");
        /*
        * create the error
        * log it and send it
        * */
        LOG.error("YOU NEED TO HANDLE BindException Errors");
        LOG.error("com.blood.donation.app.service.DonorService.loadUserByUsername exception not handled : UserEmailNotFound || User not found exception");
        LOG.warn("ALL THE AUTHENTICATION AND AUTHORIZATION EXCEPTIONS ARE NOT HANDLED !!!!!!!!!!!!!!!!!");
    }
}
