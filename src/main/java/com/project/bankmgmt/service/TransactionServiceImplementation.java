package com.project.bankmgmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.dao.TransactionsDaoWrapper;
import com.project.bankmgmt.exceptions.AccountNotFoundException;
import com.project.bankmgmt.exceptions.InsufficientBankBalance;
import com.project.bankmgmt.exceptions.InvalidTransactionDetails;
import com.project.bankmgmt.utilityService.EmailService;
@Service
public class TransactionServiceImplementation implements TransactionService {

	@Autowired
	private TransactionsDaoWrapper transactiondao;
	@Autowired
	private EmailService emailservice;
	private Logger log=LoggerFactory.getLogger(TransactionServiceImplementation.class);
	@Override
	public TransactionsBean withdrawl(String email, double amount)
			throws InsufficientBankBalance, InvalidTransactionDetails {
		if(amount>0) {
			log.trace("Transaction inititated for withdrawl");
			return transactiondao.withdrawl(email, amount);
		}else {
			log.error("Transaction not proceeded due to invalid details");
			throw new InvalidTransactionDetails();
		}
	}

	@Override
	public TransactionsBean deposit(String email, double amount) throws InvalidTransactionDetails{
		if(amount>0) {
			log.trace("Transaction inititated for deposit");
			return transactiondao.deposit(email, amount);
		}else {
			log.error("Transaction not proceeded due to invalid details");
			throw new InvalidTransactionDetails();
		}
	}

	@Override
	public TransactionsBean transfer(String email, int receiverId, double amount)
			throws AccountNotFoundException, InsufficientBankBalance,InvalidTransactionDetails {
		if(amount>0) {
			log.trace("Transaction inititated for transfer");
			return transactiondao.transfer(email, receiverId, amount);
		}else {
			log.error("Transaction not proceeded due to invalid details");
			throw new InvalidTransactionDetails();
		}
	}

	@Override
	public List<TransactionsBean> getAllTransactions() {
		log.trace("Fetch all transactions initiated");
		return transactiondao.getAllTransactions();
	}

	@Override
	@Scheduled(cron = "0 0 21 * * *")
	public void weeklyStatements() {
		List<TransactionsBean> transactions=transactiondao.getAllTransactions();
		log.info("Daily Statements Email Sending");
		emailservice.sendEmailofUserTransactions("alok8203thakur@gmail.com", "Statement of all Transactions", transactions);
	}

}
