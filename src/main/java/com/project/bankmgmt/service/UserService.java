package com.project.bankmgmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.bean.UserInfoBean;
import com.project.bankmgmt.bean.UserKYC_UpdateBean;
@Service
public interface UserService {
	public String login(UserInfoBean loginInfo);
	public UserInfoBean addUser(UserInfoBean userinfo);
	public UserInfoBean updateUser(UserKYC_UpdateBean userinfo,String email);
	public UserInfoBean checkBalance(String email);
	public List<TransactionsBean> viewTransactions(String email);
}
