package com.app.ttd.jpa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.ttd.dto.UserDetails;
import com.app.ttd.entity.UserDetailsEntity;
import com.app.ttd.repository.LdapJPARepository;
import com.app.ttd.util.object.mapper.ObjectMapperUtil;
import com.app.ttd.util.object.mapper.ObjectsConverter;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private LdapJPARepository repository;
	
	@Override
	public String saveUser(UserDetails usr) {
		UserDetailsEntity entity=new UserDetailsEntity();
		ObjectsConverter.convertDtoObjToEntity(usr, entity);
		entity=repository.save(entity);
		ObjectsConverter.convertEntityToDtoObj(entity, usr);
		if(entity!=null) {
			return " User Data Save....!";
		}else {
			return "User Data Not Save Please Check....!";
		}
	}
	
	@Override
	public UserDetails findByUserId(String userId) {
		UserDetailsEntity entity=repository.findByUserId(userId);
		UserDetails dtoObj=new UserDetails();
		ObjectsConverter.convertEntityToDtoObj(entity, dtoObj);
		return dtoObj;
	}
	
	@Override
	public void activateUsers( String userId,boolean activateUser) {
		repository.activateUsers(userId,activateUser);
		//return activate;
	}
	
	@Override
	public List<UserDetails> getInactiveUsers(){
		List<UserDetailsEntity> getInactiveUsers=repository.getInactiveUsers();
		List<UserDetails> dtoList = ObjectMapperUtil.mapAll(getInactiveUsers, UserDetails.class);
		System.out.println("Inactive users details here..."+getInactiveUsers.toString());
		return dtoList;
	}
}
