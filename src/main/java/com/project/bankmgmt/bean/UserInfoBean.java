package com.project.bankmgmt.bean;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserInfoBean {
	private int userId;
	@NotEmpty(message = "Name must have a value")
	private String userName;
	@Range(min=18,max=100,message = "Age must be between 18 to 100 years")
	@NotNull
	private int userAge;
	private List<Integer> transactions=new ArrayList<Integer>();
	@Nonnull
	@Range(min=0,max=10000000,message="Deposit amount must be above 0 and less than 1Cr")
	private double userBalance;
	@NotNull(message="Email must have a value")
	@Email(message="please provide a valid email")
	private String userEmail;
	@NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	private String userPassword;
	
	public UserInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public List<Integer> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Integer> transactions) {
		this.transactions = transactions;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
