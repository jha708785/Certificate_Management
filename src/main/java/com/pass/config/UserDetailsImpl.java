package com.pass.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pass.entites.User;
import com.pass.repository.UserRepository;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User u = userRepo.findByEmail(username);

		if (u == null)
		{
			throw new UsernameNotFoundException("User Not Exist");
		}
		else
		{

			return new CustomUserDetails(u);
		}

	}

}
