package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer newTransfer, int id, int account_Id_To);

    Transfer getTransferByTransferId(int transferId);

    int findAccountIdByUserId(int userId);

    BigDecimal updateBalanceWhenUserSendsMoney(int accountTo, int accountFrom, BigDecimal amount) throws Exception;

    List<Transfer> getTransfers(int accountId);
}

/*
public List<Transfers> getAllTransfers(int userId)
public String sendTransfer(int userFrom, int userTo, BigDecimal amount);
public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
public List<Transfers> getPendingRequests(int userId);
public String updateTransferRequest(Transfers transfer, int statusId);

 */