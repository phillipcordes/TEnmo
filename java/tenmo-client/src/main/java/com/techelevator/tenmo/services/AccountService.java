package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.http.HttpHeaders;
import java.security.Principal;

public class AccountService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();



    public AccountService(String apiURL) {
        API_BASE_URL = apiURL;
    }

}
