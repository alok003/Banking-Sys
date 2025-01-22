package com.project.bankmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.bean.UserInfoBean;
import com.project.bankmgmt.bean.UserKYC_UpdateBean;
import com.project.bankmgmt.service.UserServiceImplementation;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	private UserServiceImplementation userservice;
	@RequestMapping(value="/user/login",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody UserInfoBean loginInfo){
		String token=userservice.login(loginInfo);
		return new ResponseEntity<String>(token,HttpStatus.OK);
	}
	@RequestMapping(value="/user/adduser",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody @Valid UserInfoBean userinfo,Errors errors){
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors()+" ",HttpStatus.BAD_REQUEST);
		}else {
			UserInfoBean user=userservice.addUser(userinfo);
			return new ResponseEntity<String>("User added successfully with ID: "+user.getUserId(),HttpStatus.CREATED);
		}
	}
	@RequestMapping(value="/user/updateuser",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody @Valid UserKYC_UpdateBean userinfo,Errors errors){
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors()+" ",HttpStatus.BAD_REQUEST);
		}else {
			Authentication auth=SecurityContextHolder.getContext().getAuthentication();
			String Email=auth.getName();
			UserInfoBean user=userservice.updateUser(userinfo,Email);
			return new ResponseEntity<String>("User updated successfully with ID: "+user.getUserId(),HttpStatus.OK);
		}
	}
	@RequestMapping(value="/user/checkbalance",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkBalance(){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String Email=auth.getName();
		UserInfoBean user=userservice.checkBalance(Email);
		return new ResponseEntity<String>("Balance fetched sucessfully:"+user.getUserBalance(),HttpStatus.OK);
	}
	@RequestMapping(value="user/gettransactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TransactionsBean>> getTransactions(){
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		String Email=auth.getName();
		List<TransactionsBean> transactionlist=userservice.viewTransactions(Email);
		return new ResponseEntity<List<TransactionsBean>>(transactionlist,HttpStatus.OK);
	}

}
