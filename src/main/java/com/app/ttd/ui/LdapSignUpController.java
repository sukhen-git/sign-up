package com.app.ttd.ui;

import static java.util.Comparator.comparing;

import java.util.Collections;
import java.util.List;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ttd.dto.UserDetails;
import com.app.ttd.entity.InactiveUsers;
import com.app.ttd.entity.UserDetailsEntity;
import com.app.ttd.jpa.service.UserDetailsService;
import com.app.ttd.repository.LdapJPARepository;
import com.app.ttd.repository.LdapSignupRepository;
import com.app.ttd.util.LdapDepartments;
import com.app.ttd.util.LdapRolse;
import com.app.ttd.util.SignupObject;

@RestController
@RequestMapping("/ldapSignUp")
public class LdapSignUpController {

	@Autowired
	private LdapSignupRepository ldapRepository;
	//@Autowired
	//private LdapJPARepository jpaRepository;
	
	@Autowired
	private UserDetailsService service;
	
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> userSignUp(@RequestBody SignupObject singupObj, @RequestParam("dept") String dept)  {
		
		if(checkUserExist(singupObj.getUid())) {
			return new ResponseEntity<String>("User Id Already Exists....", HttpStatus.BAD_REQUEST);
		} else {
			
			UserDetails userDtls=new UserDetails();
			userDtls.setUserId(singupObj.getUid());
			userDtls.setDepartmentName(dept);
			userDtls.setActivateUser(false);
			userDtls.setCreatedBy(singupObj.getUid());
			userDtls.setLastmodifiedBy(singupObj.getUid());
			service.saveUser(userDtls);
			//jpaRepository.save(user);
			ldapRepository.createUser(singupObj);
			//repository.addMemberToGroup(dept,role, usr);
			return new ResponseEntity<String>("User Created Successfully..", HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/getInActivateUser")
	public ResponseEntity <List<UserDetails>> getInactiveUserDetails(){
		List<UserDetails> getInactiveUsers=service.getInactiveUsers();
		return new ResponseEntity <List<UserDetails>>(getInactiveUsers, HttpStatus.OK);
	}
	
	@PostMapping(path="/activateUser")	
	public ResponseEntity<String> activateUser(@RequestParam("userId")String userId, @RequestParam("dept")String dept, @RequestParam("role")String role){		
		if(checkactivateUserRequest(userId,dept,role)) {
			ldapRepository.addMemberToGroup1(dept, role, userId);
			UserDetails usrDtls=service.findByUserId(userId);
			usrDtls.setActivateUser(true);
			usrDtls.setLastmodifiedBy("Admin_"+dept);
			
			service.saveUser(usrDtls);
			//System.out.println("usrDtls"+usrDtls.getUserId()+","+usrDtls.getLastmodifiedby()+","+usrDtls.getDepartmentName()+","+usrDtls.getLastmodifieddate()+",");
			
			//service.activateUsers(userId,true);
			return new ResponseEntity<String>("User Activated Successfully..", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("User Not Activated Please check....", HttpStatus.OK);
		}
	}
	
	
	@PostMapping(path="/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestParam("emailId")String emailId,@RequestParam("newPassword")String newPassword){
		if(emailId!=null && emailId!="" && newPassword!=null && newPassword!="") {
			String passwordUpdate=ldapRepository.changePassword(emailId, newPassword);
			return new ResponseEntity<String>(passwordUpdate, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Please check the request....", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/getAllDepartment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LdapDepartments>> getDepartments() {
		List<LdapDepartments> getDepartments = ldapRepository.getAllDepartment();
		Collections.sort(getDepartments, comparing(LdapDepartments::getDepartmentName));
		return new ResponseEntity<List<LdapDepartments>>(getDepartments, HttpStatus.OK);
	}

	@GetMapping(path = "/getAllRoles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LdapRolse>> getAllRoles() {
		List<LdapRolse> getRoles = ldapRepository.getAllRoles();
		Collections.sort(getRoles, comparing(LdapRolse::getRoleNames));
		return new ResponseEntity<List<LdapRolse>>(getRoles, HttpStatus.OK);
	}
	
	private boolean checkUserExist(String uid) {
		boolean success=false;
		if(uid!=null && uid!="") {
		List<String> getuid=ldapRepository.getUID(uid);
		if(getuid!=null && getuid.size()!=0) {
			success=true;;
		}else {
			success=false;
		}
		}else {
			success=false;
		}
		return success;
	}
	
	private boolean checkactivateUserRequest(String userId,String dept,String role) {
		boolean flag=false;
		if(userId!=null && userId!="" && dept!=null && dept!="" && role!=null && role!="") {
			flag=true;
		}else {
			flag=false;
		}
		return flag;
	}
}
