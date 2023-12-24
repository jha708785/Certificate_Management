package com.pass.service;

import java.util.List;

import com.pass.entites.ApplyForm;
import com.pass.entites.User;

public interface UserService {

	public User addUser(User user);

	public boolean checkEmail(String email); 

	public User updateUser(User user);

	public ApplyForm saveApplyForm(ApplyForm ap);

	public List<ApplyForm> getFormByUser(long userId);
	
	public ApplyForm getFormById(long id);

}
