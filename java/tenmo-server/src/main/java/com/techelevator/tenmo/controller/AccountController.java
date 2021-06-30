package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.JbdcAccountsDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import com.techelevator.tenmo.dao.JbdcUserDao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts") //calls which path to take
@PreAuthorize("isAuthenticated()")

public class AccountController {

    private UserDao userDao;
    private AccountsDao accountsDao;

    public AccountController(AccountsDao accountsDao, UserDao userDao) { //dependency injection for daos
        this.accountsDao = accountsDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET) //map to balance
    public BigDecimal getBalance(Principal user) {return accountsDao.getBalance(user.getName());}
}
