package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransferController {

    private TransferDao transferDao;
    private AccountsDao accountsDao;

    public TransferController(AccountsDao accountsDao, TransferDao transferDao){
        this.accountsDao = accountsDao;
        this.transferDao = transferDao;
    }

    //send/create transfer request
    @RequestMapping(path = "/account/transfer/", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer, Principal principal){
        return transferDao.createTransfer(transfer);
    }

    //request transfer request with POST

    //GET all transfers

    //GET selected transfers

    //GET all transfer requests

    //update request with PUT




}
