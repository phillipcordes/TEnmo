package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

//private AuthenticatedUser currentUser;

    public TransferService(String apiURL) {
        API_BASE_URL = apiURL;
    } //(String apiURL, AuthenticatedUser currentUsr) this.currentUser = currentUser;


    //return list of users
    public void listUsers()  {
        User[] users;
        try {
            users = restTemplate.getForObject(API_BASE_URL + "users", User[].class);
            for(User user: users){
                System.out.println("Username: " + user.getUsername() + " - UserId: " + user.getId());
            }
        } catch (RestClientResponseException ex) {
            System.out.println("Error"); //new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }


    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
