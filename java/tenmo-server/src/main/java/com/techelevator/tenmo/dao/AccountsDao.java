package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountsDao {

//return balance
    BigDecimal getBalance(String username);


}
