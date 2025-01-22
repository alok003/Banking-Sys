package com.project.bankmgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.exceptions.AccountNotFoundException;
import com.project.bankmgmt.exceptions.InsufficientBankBalance;
import com.project.bankmgmt.exceptions.InvalidTransactionDetails;

@Service
public interface TransactionService {
	public TransactionsBean withdrawl(String email,double amount) throws InsufficientBankBalance,InvalidTransactionDetails;
	public TransactionsBean deposit(String email,double amount) throws InvalidTransactionDetails;
	public TransactionsBean transfer(String email,int receiverId,double amount)throws AccountNotFoundException,InsufficientBankBalance,InvalidTransactionDetails;
	public List<TransactionsBean> getAllTransactions();
	public void weeklyStatements();
}
