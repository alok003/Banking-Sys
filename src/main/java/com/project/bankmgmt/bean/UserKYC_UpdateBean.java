package com.project.bankmgmt.bean;



import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserKYC_UpdateBean {
	@NotEmpty(message = "Name must have a value")
	private String userName;
	@Range(min=18,max=100,message = "Age must be between 18 to 100 years")
	@NotNull
	private int userAge;
	@NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
	private String userPassword;
	public UserKYC_UpdateBean() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
