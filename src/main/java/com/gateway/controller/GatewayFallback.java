package com.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @project SpringCloudGateway
 * @author Digvijay Singh
 */
@RestController
@RequestMapping("/fallback")
public class GatewayFallback {

    @GetMapping("/testService")
    public String fallback() {

        return "The service is not available";
    }

}