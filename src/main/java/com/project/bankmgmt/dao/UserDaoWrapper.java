package com.project.bankmgmt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.bankmgmt.bean.TransactionsBean;
import com.project.bankmgmt.bean.UserInfoBean;
import com.project.bankmgmt.bean.UserKYC_UpdateBean;
import com.project.bankmgmt.entity.Transactions;
import com.project.bankmgmt.entity.UserInfo;
import com.project.bankmgmt.utilityService.EmailService;



@Repository
public class UserDaoWrapper {
	@Autowired
	private UserDao userdao;
	@Autowired 
	private TransactionsDao transactiondao;
	@Autowired
	private TransactionsDaoWrapper tdaowrapper;
	@Autowired
	private EmailService emailservice;
	private Logger log=LoggerFactory.getLogger(UserDaoWrapper.class);
	public UserInfoBean addUser(UserInfoBean userbean) {
		UserInfo user=cnvBeanToEntity(userbean);
		userdao.save(user);
		log.info("User Created Sucessfully with id {}",user.getUserId());
		return cnvEntityToBean(user);
	}
	public UserInfoBean updateUser(UserKYC_UpdateBean userbean,String email){
		UserInfo user=userdao.findByUserEmail(email);
		UserInfoBean updateduser=cnvEntityToBean(user);
		updateduser.setUserName(userbean.getUserName());
		updateduser.setUserAge(userbean.getUserAge());
		updateduser.setUserPassword(userbean.getUserPassword());
		userdao.save(cnvBeanToEntity(updateduser));
		log.info("User updated sucessfully");
		return updateduser;
	}
	public UserInfoBean checkBalance(String email){
		UserInfo user=userdao.findByUserEmail(email);
		log.info("Balance fetched sucessfully");
		return cnvEntityToBean(user);
	}
	public List<TransactionsBean> viewTransactions(String email){
		UserInfo user=userdao.findByUserEmail(email);
		List<TransactionsBean> transaction=new ArrayList<TransactionsBean>();
		for(int t:user.getTransactions()) {
			Optional<Transactions> transactionextraction=transactiondao.findById(t);
			if(!transactionextraction.isEmpty()) {
				transaction.add(tdaowrapper.cnvEntityToBean(transactionextraction.get()));
			}
		}
		emailservice.sendEmailofUserTransactions(user.getUserEmail(), "Statement Of Transactions", transaction);
		log.info("transactions emailed and displayed sucessfully");
		return transaction;
	}
	UserInfo cnvBeanToEntity(UserInfoBean userinfo) {
		UserInfo userentity=new UserInfo();
		BeanUtils.copyProperties(userinfo, userentity);
		return userentity;
	}
	UserInfoBean cnvEntityToBean(UserInfo userentity) {
		UserInfoBean userbean=new UserInfoBean();
		BeanUtils.copyProperties(userentity, userbean);
		return userbean;
	}
}
