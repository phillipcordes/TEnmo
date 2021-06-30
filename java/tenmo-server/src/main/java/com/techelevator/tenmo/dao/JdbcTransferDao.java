package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao implements TransferDao {


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transfer getTransferByTransferId(int transferId) {

        Transfer transfer = null;

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount" +
                "FROM transfers" +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;

    }

    public Transfer createTransfer(Transfer newTransfer) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "VALUES (2, 2,?,?,?) " +
                "RETURNING transfer_id;";
        int newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransfer_type_id(),
                newTransfer.getTransfer_status_id(), newTransfer.getAccount_from(), newTransfer.getAccount_to(),
                newTransfer.getAmount());
        return getTransferByTransferId(newId);
    }

    //Get All Transfers
    //Send Transfer
    //Request Transfer
    //Get Pending Request
    //Update Transfer Request

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rowSet.getInt("tranfer_id"));
        transfer.setTransfer_type_id(rowSet.getInt("tranfer_type_id"));
        transfer.setTransfer_status_id(rowSet.getInt("transfer_status_id"));
        transfer.setAccount_to(rowSet.getInt("account_to"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }
//add try/catch block to mapRow when transferring amounts?
}
