package com.daniel.springcloud.msvc.products.infrastructure.adapter.in.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/test")
public class TestApiController {

    @Value("${server.port}")
    private String port;

    @GetMapping("")
    public ResponseEntity<String> getMethodName() throws UnknownHostException{
        String hostName = InetAddress.getLocalHost().getHostName();
        return ResponseEntity.ok("Basic Store Api running in " + hostName + " on port " + port);
    }
    
    
}
