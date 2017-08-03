package com.vets.dao;

import com.vets.model.Transaction;

import java.util.List;

/**
 * Created by mallickp on 8/2/2017.
 */
public interface TransactionDao {
    public boolean addTransaction(Transaction transaction) throws Exception;
    public Transaction getTransactionById(long transactionId) throws Exception;
    public List<Transaction> getTransactionList() throws Exception;
    public boolean deleteTransaction(long transactionId) throws Exception;

}
