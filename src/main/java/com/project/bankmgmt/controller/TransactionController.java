package com.project.bankmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.exceptions.AccountNotFoundException;
import com.project.bankmgmt.exceptions.InsufficientBankBalance;
import com.project.bankmgmt.exceptions.InvalidTransactionDetails;
import com.project.bankmgmt.service.TransactionServiceImplementation;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionServiceImplementation transactionservice;
	@RequestMapping(value="/transaction/{amount}/withdraw",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> withdrawl(@PathVariable double amount) {
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			String Email=auth.getName();
			TransactionsBean tb=transactionservice.withdrawl(Email, amount);
			return new ResponseEntity<String>("Transaction Sucessfull with Id:"+tb.getTransactionId()+" and amount: "+tb.getAmount(), HttpStatus.OK);
		}catch(InsufficientBankBalance ibb) {
			return new ResponseEntity<String>(ibb.getMessage(), HttpStatus.OK);
		}catch(InvalidTransactionDetails itd) {
			return new ResponseEntity<String>(itd.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(value="/transaction/{amount}/deposit",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deposit(@PathVariable double amount) {
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			String Email=auth.getName();
			TransactionsBean tb=transactionservice.deposit(Email, amount);
			return new ResponseEntity<String>("Transaction Sucessfull with Id:"+tb.getTransactionId()+" and amount: "+tb.getAmount(), HttpStatus.OK);
		}catch(InvalidTransactionDetails itd) {
			return new ResponseEntity<String>(itd.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(value="/transaction/{amount}/transfer/{receiverid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> transfer(@PathVariable double amount,@PathVariable int receiverid) {
		try {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			String Email=auth.getName();
			TransactionsBean tb=transactionservice.transfer(Email,receiverid,amount);
			return new ResponseEntity<String>("Transaction Sucessfull with Id:"+tb.getTransactionId()+" and amount: "+tb.getAmount(), HttpStatus.OK);
		}catch(InsufficientBankBalance ibb) {
			return new ResponseEntity<String>(ibb.getMessage(), HttpStatus.OK);
		}catch(InvalidTransactionDetails itd) {
			return new ResponseEntity<String>(itd.getMessage(), HttpStatus.OK);
		}catch(AccountNotFoundException anfe) {
			return new ResponseEntity<String>(anfe.getMessage(), HttpStatus.OK);
		}
	}
	@RequestMapping(value="/transaction/getalltransactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionsBean>> getAllTransactions(){
		List<TransactionsBean> alltransactions=transactionservice.getAllTransactions();
		return new ResponseEntity<List<TransactionsBean>>(alltransactions,HttpStatus.OK);
	}
}
