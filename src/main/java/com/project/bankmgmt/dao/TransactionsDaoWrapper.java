package com.project.bankmgmt.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.entity.Transactions;
import com.project.bankmgmt.entity.UserInfo;
import com.project.bankmgmt.exceptions.AccountNotFoundException;
import com.project.bankmgmt.exceptions.InsufficientBankBalance;
import com.project.bankmgmt.exceptions.InvalidTransactionDetails;
import com.project.bankmgmt.utilityService.EmailService;

@Repository
public class TransactionsDaoWrapper {
	@Autowired
	private TransactionsDao transactiondao;
	@Autowired
	private UserDao userdao;
	@Autowired
	private EmailService emailservice;
	private Logger log=LoggerFactory.getLogger(TransactionsDaoWrapper.class);
	
	public TransactionsBean withdrawl(String email,double amount) throws InsufficientBankBalance {
		UserInfo user=userdao.findByUserEmail(email);
		if(user.getUserBalance()>=amount) {
			Transactions makeTransaction=new Transactions();
			makeTransaction.settDate(LocalDateTime.now());
			makeTransaction.setAmount(amount);
			makeTransaction.setSenderId(user.getUserId());
			makeTransaction.setTransactionType("WITHDRAWL");
			transactiondao.save(makeTransaction);
			TransactionsBean maketransactionbean=cnvEntityToBean(makeTransaction);
			user.setUserBalance(user.getUserBalance()-amount);
			List<Integer> newlist=user.getTransactions();
			newlist.add(maketransactionbean.getTransactionId());
			user.setTransactions(newlist);
			userdao.save(user);
			emailservice.sendEmailOfTransaction(user.getUserEmail(),"Withdrawl amount of:"+amount,"DEBITED",maketransactionbean);
			log.info("Transaction withdrawl sucessfull with id {}",maketransactionbean.getTransactionId());
			return maketransactionbean;
		}else {
			log.error("Transaction failed due to insufficient bank balance");
			throw new InsufficientBankBalance();
		}
	}
	public TransactionsBean deposit(String email,double amount){
		UserInfo user=userdao.findByUserEmail(email);
		Transactions makeTransaction=new Transactions();
		makeTransaction.settDate(LocalDateTime.now());
		makeTransaction.setAmount(amount);
		makeTransaction.setReceiverId(user.getUserId());
		makeTransaction.setTransactionType("DEPOSIT");
		transactiondao.save(makeTransaction);
		TransactionsBean maketransactionbean=cnvEntityToBean(makeTransaction);
		user.setUserBalance(user.getUserBalance()+amount);
		List<Integer> newlist=user.getTransactions();
		newlist.add(maketransactionbean.getTransactionId());
		user.setTransactions(newlist);
		userdao.save(user);
		emailservice.sendEmailOfTransaction(user.getUserEmail(),"Deposit of amount:"+amount,"CREDITED",maketransactionbean);
		log.info("Transaction deposit sucessfull with id {}",maketransactionbean.getTransactionId());
		return maketransactionbean;
	}
	public TransactionsBean transfer(String email,int receiverId,double amount) throws AccountNotFoundException,InsufficientBankBalance, InvalidTransactionDetails {
		UserInfo usersender=userdao.findByUserEmail(email);
		Optional<UserInfo> user=userdao.findById(receiverId);
		if(!user.isEmpty()) {
			UserInfo userreceiver=user.get();
			if(usersender.getUserId()==userreceiver.getUserId())
				throw new InvalidTransactionDetails();
			else if(usersender.getUserBalance()>=amount) {
				Transactions makeTransaction=new Transactions();
				makeTransaction.settDate(LocalDateTime.now());
				makeTransaction.setAmount(amount);
				makeTransaction.setSenderId(usersender.getUserId());
				makeTransaction.setReceiverId(receiverId);
				makeTransaction.setTransactionType("TRANSFER");
				transactiondao.save(makeTransaction);
				TransactionsBean maketransactionbean=cnvEntityToBean(makeTransaction);
				usersender.setUserBalance(usersender.getUserBalance()-amount);
				userreceiver.setUserBalance(userreceiver.getUserBalance()+amount);
				List<Integer> newlist=usersender.getTransactions();
				newlist.add(maketransactionbean.getTransactionId());
				usersender.setTransactions(newlist);
				newlist=userreceiver.getTransactions();
				newlist.add(makeTransaction.getTransactionId());
				userreceiver.setTransactions(newlist);
				userdao.save(usersender);
				userdao.save(userreceiver);
				emailservice.sendEmailOfTransaction(usersender.getUserEmail(),"Account Debited with amount:"+amount,"DEBITED",maketransactionbean);
				emailservice.sendEmailOfTransaction(userreceiver.getUserEmail(),"Account Credited with amount:"+amount,"CREDITED",maketransactionbean);
				log.info("Transaction transfer sucessfull with id {}",maketransactionbean.getTransactionId());
				return maketransactionbean;
			}else {
				log.error("Transaction failed due to insufficient bank balance");
				throw new InsufficientBankBalance();
			}
		}else {
			log.error("Transaction failed due to Account not found");
			throw new AccountNotFoundException();
		}
	}
	public List<TransactionsBean> getAllTransactions(){
		List<Transactions> alltransaction=transactiondao.findAll();
		List<TransactionsBean> alltransactionbean=new ArrayList<TransactionsBean>();
		for(Transactions t:alltransaction) {
			alltransactionbean.add(cnvEntityToBean(t));
		}
		log.info("All transactions displayed");
		return alltransactionbean;
	}
	Transactions cnvBeanToEntity(TransactionsBean transactionsbean) {
		Transactions transactions=new Transactions();
		BeanUtils.copyProperties(transactionsbean, transactions);
		return transactions;
	}
	TransactionsBean cnvEntityToBean(Transactions transactions) {
		TransactionsBean transactionsbean=new TransactionsBean();
		BeanUtils.copyProperties(transactions, transactionsbean);
		return transactionsbean;
	}
}
