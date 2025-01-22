package com.project.bankmgmt.bean;

import java.time.LocalDateTime;

public class TransactionsBean {
	private int transactionId;
	private int senderId;
	private int receiverId;
	private String transactionType;
	private double amount;
	private LocalDateTime tDate;
	public TransactionsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDateTime gettDate() {
		return tDate;
	}
	public void settDate(LocalDateTime tDate) {
		this.tDate = tDate;
	}
	
}
