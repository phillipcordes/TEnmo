package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface TransferDao {

    Transfer createTransfer(Transfer newTransfer, int id, int account_Id_To);

    Transfer getTransferByTransferId(int transferId);

    int findAccountIdByUserId(int userId);

    void updateBalanceWhenUserSendsMoney(int accountTo, int accountFrom, BigDecimal amount) throws Exception;
}

/*
public List<Transfers> getAllTransfers(int userId)
public String sendTransfer(int userFrom, int userTo, BigDecimal amount);
public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
public List<Transfers> getPendingRequests(int userId);
public String updateTransferRequest(Transfers transfer, int statusId);

 */