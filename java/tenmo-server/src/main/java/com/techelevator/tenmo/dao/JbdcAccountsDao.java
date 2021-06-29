package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JbdcAccountsDao implements AccountsDao{
    private JdbcTemplate jdbcTemplate;


    @Override
    public Integer getBalance(String username) {
        String sql = "SELECT user_id FROM users WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
        }
        sql = "SELECT balance FROM accounts WHERE user_id = ?;";
       Integer balance = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return balance;
    }

}
