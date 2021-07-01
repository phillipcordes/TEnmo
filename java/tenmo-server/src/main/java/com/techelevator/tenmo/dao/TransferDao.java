package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    Transfer createTransfer(Transfer newTransfer, int id);

    Transfer getTransferByTransferId(int transferId);

}

/*
public List<Transfers> getAllTransfers(int userId)
public String sendTransfer(int userFrom, int userTo, BigDecimal amount);
public String requestTransfer(int userFrom, int userTo, BigDecimal amount);
public List<Transfers> getPendingRequests(int userId);
public String updateTransferRequest(Transfers transfer, int statusId);

 */