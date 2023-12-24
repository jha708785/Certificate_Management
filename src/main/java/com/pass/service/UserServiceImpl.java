package com.pass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pass.entites.ApplyForm;
import com.pass.entites.User;
import com.pass.repository.ApplyFormRepository;
import com.pass.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ApplyFormRepository apRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User addUser(User user) 
	{
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public boolean checkEmail(String email)
	{
		return userRepo.existsByEmail(email);
	}

	@Override
	public User updateUser(User user)
	{
		User oldUser = userRepo.findById(user.getId()).get();
		user.setPassword(oldUser.getPassword());
		return userRepo.save(user);
	}

	@Override
	public ApplyForm saveApplyForm(ApplyForm ap)
	{
		ap.setStatus("Review");
		ap.setAppointmentDate("Not Schedule");
		ap.setOfficeAddress("NA");
		return apRepo.save(ap);
	}

	@Override
	public List<ApplyForm> getFormByUser(long userId) {
		return apRepo.findByUserId(userId);
	}

	@Override
	public ApplyForm getFormById(long id) {
		return apRepo.findById(id).get();
	}

}
