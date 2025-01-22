package com.project.bankmgmt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.bean.UserInfoBean;
import com.project.bankmgmt.bean.UserKYC_UpdateBean;
import com.project.bankmgmt.dao.UserDaoWrapper;
import com.project.bankmgmt.utilityService.JwtUtil;


@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserDaoWrapper userdao;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtil jwtutil;
	private Logger log=LoggerFactory.getLogger(UserServiceImplementation.class);
	private static final PasswordEncoder passwordencoder=new BCryptPasswordEncoder();
	
	@Override
	public String login(UserInfoBean loginInfo) {
		log.info("Login instantiated");
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginInfo.getUserEmail(), loginInfo.getUserPassword()));
        if (authentication.isAuthenticated()) {
        	log.info("JWT token created");
            return jwtutil.generateToken(loginInfo.getUserEmail());
        } else {
        	log.info("credentials wrong");
            return "fail";
        }
	}
	
	@Override
	public UserInfoBean addUser(UserInfoBean userinfo) {
		log.trace("User Registration initiated");
		userinfo.setUserPassword(passwordencoder.encode(userinfo.getUserPassword()));
		return userdao.addUser(userinfo);
	}

	@Override
	public UserInfoBean updateUser(UserKYC_UpdateBean userinfo,String email){
		log.trace("User Update initiated");
		userinfo.setUserPassword(passwordencoder.encode(userinfo.getUserPassword()));
		return userdao.updateUser(userinfo,email);
	}

	@Override
	public UserInfoBean checkBalance(String email){
		log.trace("User Balance Fetching initiated");
		return userdao.checkBalance(email);
	}

	@Override
	public List<TransactionsBean> viewTransactions(String email){
		log.trace("User Transactions fetching");
		return userdao.viewTransactions(email);
	}

}
