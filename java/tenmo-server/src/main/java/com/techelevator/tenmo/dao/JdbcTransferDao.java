package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transfer getTransferByTransferId(int transferId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;

    }

    public int findAccountIdByUserId(int userId) {
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    public Transfer createTransfer(Transfer newTransfer, int id, int account_Id_to) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2, 2,?,?,?) " +
                "RETURNING transfer_id;";
        int newId = jdbcTemplate.queryForObject(sql, Integer.class, id, account_Id_to,
                newTransfer.getAmount());
        return getTransferByTransferId(newId);
    }

    //Get All Transfers
    //Send Transfer
    //Request Transfer
    //Get Pending Request
    //Update Transfer Request

    public BigDecimal updateBalanceWhenUserSendsMoney(int accountTo, int accountFrom, BigDecimal amount) throws Exception {
        String sqlAmountCheck = "SELECT balance FROM accounts WHERE account_id = ?; ";
        BigDecimal currentBalance = jdbcTemplate.queryForObject(sqlAmountCheck, BigDecimal.class, accountFrom);
        if (currentBalance.compareTo(amount) == -1) {
            throw new Exception();
        } else {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?; " +
                    "UPDATE accounts SET balance = balance - ? WHERE account_id = ?; ";
            jdbcTemplate.update(sql, amount, accountTo, amount, accountFrom);
            return jdbcTemplate.queryForObject(sqlAmountCheck, BigDecimal.class, accountFrom);
        }
    }

    public List<Transfer> getTransfers(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id,transfer_type_id,transfer_status_id,account_from,account_to,amount " +
                "FROM transfers t " +
                "JOIN accounts a ON a.account_id = t.account_from " +
                "WHERE a.account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getInt("transfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setAccount_to(rowSet.getInt("account_to"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
//add try/catch block to mapRow when transferring amounts?
}

