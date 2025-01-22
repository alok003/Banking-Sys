package com.project.bankmgmt.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bankmgmt.entity.Transactions;

@Repository
public interface TransactionsDao extends JpaRepository<Transactions, Integer> {

}
