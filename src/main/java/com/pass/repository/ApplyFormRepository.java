package com.pass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pass.entites.ApplyForm;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

	//Apply from by user..
	public List<ApplyForm> findByUserId(long uid);

}
