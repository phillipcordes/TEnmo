package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransferController {

    private TransferDao transferDao;
    private AccountsDao accountsDao;
    private UserDao userDao;

    public TransferController(UserDao userDao, AccountsDao accountsDao, TransferDao transferDao){
        this.accountsDao = accountsDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    //send transfer request
    @RequestMapping(path = "/accounts/transfer", method = RequestMethod.POST)
    public Transfer sendTransfer(@RequestBody Transfer transfer, Principal principal) {
        String loggedInUserName = principal.getName();
        int loggedInUserId = userDao.findIdByUsername(loggedInUserName);
        int accountId = transferDao.findAccountIdByUserId(loggedInUserId);
        int accountIdTo = transferDao.findAccountIdByUserId(transfer.getUser_Id());
        return transferDao.createTransfer(transfer, accountId,accountIdTo);
    }
/*
    //request transfer request with POST
    @RequestMapping(path="request", method = RequestMethod.POST)
    public String requestTransferRequest(@RequestBody Transfers transfer){
        String results = transfersDAO.requestTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        return results;

//List all Transfers
    @RequestMapping(value = "request/{id}", method = RequestMethod.GET)
    public List<Transfers> getAllTransfersDAO


    //GET all transfers
    @RequestMapping(path ="account/transfers/{id}", method = RequestMethod.GET)
    public List<Transfers> getAllMyTransfers (@PathVariable int id) {
        List<Transfers> output = transfersDAO.getAllTansfers(id);
           return output;


    //GET selected transfers
    @RequestMapping(path = "transfers/{id}", method = RequestMethod.GET)
        public Transfers getSelectedTransfer(@PathVariable int id)
        Transfers transfers = transfersDAO.getTransferById(id);
            return transfer;


    //update request with PUT
    @RequestMapping(path = "transfer/status/(statusId)", method = RequestMethod.PUR)
        public String updateRequest(@RequestBody Transfers transfers, @PathVariable int statusId) {
            String output = transfersDAO.updateTransferRequest(transfer, statusId);
            return output;


 */
}
