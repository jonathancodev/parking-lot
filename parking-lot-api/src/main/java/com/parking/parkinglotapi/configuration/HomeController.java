package com.parking.parkinglotapi.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET )
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
