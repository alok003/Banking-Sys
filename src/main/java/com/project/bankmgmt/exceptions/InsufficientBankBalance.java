package com.project.bankmgmt.exceptions;

public class InsufficientBankBalance extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientBankBalance(){
		super("Insufficient Bank Balance");
	}
}
