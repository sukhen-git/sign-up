package com.app.ttd.jpa.service;

import java.util.List;

import com.app.ttd.dto.UserDetails;
import com.app.ttd.entity.UserDetailsEntity;

public interface UserDetailsService {
	
	public UserDetails findByUserId(String userId);
	public String saveUser(UserDetails usr);
	public void activateUsers( String userId,boolean activateUser);
	public List<UserDetails> getInactiveUsers();
}
