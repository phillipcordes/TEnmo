package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class JbdcAccountsDao implements AccountsDao{
    private JdbcTemplate jdbcTemplate;

    public JbdcAccountsDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(String username) {
       String sql = "SELECT balance FROM accounts a JOIN users u ON u.user_id = a.user_id WHERE username = ?;";
        return jdbcTemplate.queryForObject(sql,BigDecimal.class,username);
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getLong("user_id"));
        account.setAccountId(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}
