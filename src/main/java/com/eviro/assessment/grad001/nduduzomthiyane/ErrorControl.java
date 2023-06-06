package com.eviro.assessment.grad001.nduduzomthiyane;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControl implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
        return "THE REQUESTED URL IS NOT PART OF OUR " +
                "SCHEMA, PLEASE INPUT A PROPER URL";
    }

}
