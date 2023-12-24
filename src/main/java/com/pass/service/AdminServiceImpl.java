package com.pass.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pass.entites.ApplyForm;
import com.pass.entites.Office;
import com.pass.entites.User;
import com.pass.repository.ApplyFormRepository;
import com.pass.repository.OfficeRepository;
import com.pass.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ApplyFormRepository apRepo;

	@Autowired
	private OfficeRepository officeRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<ApplyForm> getAllForm()
	{
		return apRepo.findAll();
	}

	@Override
	public ApplyForm getFormById(long id)
	{
		return apRepo.findById(id).get();
	}

	@Override
	public ApplyForm updateStatus(String st, long id) 
	{

		ApplyForm ap = apRepo.findById(id).get();
		ap.setStatus(st);
		return apRepo.save(ap);
	}

	@Override
	public Office addOffice(Office of)
	{
		return officeRepo.save(of);
	}

	@Override
	public List<Office> getAllOffice()
	{
		return officeRepo.findAll();
	}

	@Override
	public Office getOfficeById(long id)
	{
		return officeRepo.findById(id).get();
	}

	@Override
	public boolean deleteOffice(long id)
	{
		Office of = officeRepo.findById(id).get();

		if (of != null) {
			officeRepo.delete(of);
			return true;
		}
		return false;
	}

	@Override
	public ApplyForm updateAppointment(ApplyForm f)
	{
		ApplyForm oldAp = apRepo.findById(f.getId()).get();
		oldAp.setAppointmentDate(f.getAppointmentDate());
		oldAp.setOfficeAddress(f.getOfficeAddress());
		return apRepo.save(oldAp);
	}

	@Override
	public List<User> getAllUser()
	{
		return userRepo.findAll();
	}

	@Override
	public User getUserById(long id) 
	{
		return userRepo.findById(id).get();
	}

	@Override
	public User updateUser(User user)
	{

		User oldUser = userRepo.findById(user.getId()).get();
		user.setPassword(oldUser.getPassword());
		user.setRole(oldUser.getRole());
		return userRepo.save(user);
	}

	@Override
	public boolean deleteUser(long id) 
	{

		User oldUser = userRepo.findById(id).get();
		if (oldUser != null) {
			userRepo.delete(oldUser);
			return true;
		}
		return false;
	}

}
