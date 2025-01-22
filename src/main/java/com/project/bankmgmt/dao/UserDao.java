package com.project.bankmgmt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bankmgmt.entity.UserInfo;
@Repository
public interface UserDao extends JpaRepository<UserInfo,Integer>{

	UserInfo findByUserEmail(String email);
}
