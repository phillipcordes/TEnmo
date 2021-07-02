package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransferService {
    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private Object Transfer;
    private Object List;

//private AuthenticatedUser currentUser;

    public TransferService(String apiURL) {
        API_BASE_URL = apiURL;
    } //(String apiURL, AuthenticatedUser currentUsr) this.currentUser = currentUser;


    //return list of users
    public void listUsers() {
        User[] users;
        try {
            users = restTemplate.getForObject(API_BASE_URL + "users", User[].class);
            for (User user : users) {
                System.out.println("Username: " + user.getUsername() + " - UserId: " + user.getId());
            }
        } catch (RestClientResponseException ex) {
            System.out.println("Error"); //new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

    public void sendMoney(String token) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************************************************");
        System.out.println("Please enter the userID of the person you wish to send TE Bucks to: "); //enter in userId
        int userID = Integer.parseInt(scanner.nextLine());
        System.out.println("*************************************************************************");
        System.out.println("Please enter the amount of TE Bucks you wish to send: ");
        int amount = Integer.parseInt(scanner.nextLine());
        BigDecimal amountSent = BigDecimal.valueOf(amount);
        Transfer transfer = makeTransfer(userID, amountSent);
        Transfer returnTransfer = restTemplate.exchange(API_BASE_URL + "accounts/transfer/sendmoney", HttpMethod.POST, makeAuthEntity(token, transfer), Transfer.class).getBody();


    }

    public void listTransfers(String token){
        List<Transfer> transferList = new ArrayList<>();
        try {
            transferList = restTemplate.exchange(API_BASE_URL + "/accounts/listtransfers", HttpMethod.GET, makeAuthEntity(token), List.class).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private Transfer makeTransfer(int userId, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_type_id(2);
        transfer.setTransfer_status_id(2);
        transfer.setUser_Id(userId);
        transfer.setAmount(amount);
        return transfer;
    }

    private HttpEntity makeAuthEntity(String token, Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<Transfer>(transfer, headers);
        return entity;
    }
}
