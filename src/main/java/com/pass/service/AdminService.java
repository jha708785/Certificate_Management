package com.pass.service;

import java.util.List;

import com.pass.entites.ApplyForm;
import com.pass.entites.Office;
import com.pass.entites.User;

public interface AdminService
{

	public List<ApplyForm> getAllForm();

	public ApplyForm getFormById(long id);

	public ApplyForm updateStatus(String st, long id);

	public Office addOffice(Office of);

	public List<Office> getAllOffice();

	public Office getOfficeById(long id);

	public boolean deleteOffice(long id);

	public ApplyForm updateAppointment(ApplyForm f);

	public List<User> getAllUser();

	public User getUserById(long id);

	public User updateUser(User user);

	public boolean deleteUser(long id);

}
