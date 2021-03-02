package com.app.ttd.ui;

import static java.util.Comparator.comparing;

import java.util.Collections;
import java.util.List;

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
import com.app.ttd.repository.LdapSignupRepository;
import com.app.ttd.util.LdapDepartments;
import com.app.ttd.util.LdapRolse;
import com.app.ttd.util.SignupObject;

@RestController
@RequestMapping("/ldapSignUp")
public class LdapSignUpController {

	@Autowired
	private LdapSignupRepository repository;
	
	private boolean checkUserExist(String uid) {
		boolean success=false;
		if(uid!=null && uid!="") {
		List<String> getuid=repository.getUID(uid);
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
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> userSignUp(@RequestBody SignupObject usr, @RequestParam("dept") String dept, @RequestParam("role") String role)  {
		
		if(checkUserExist(usr.getUid())) {
			return new ResponseEntity<String>("User Id Already Exists....", HttpStatus.BAD_REQUEST);
		} else {
			repository.createUser(usr);
			//repository.addMemberToGroup("IT","IT_ADMIN", usr);
			repository.addMemberToGroup(dept,role, usr);
			return new ResponseEntity<String>("User Created Successfully..", HttpStatus.OK);
		}
	}
	
	@PostMapping(path="/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestParam("emailId")String emailId,@RequestParam("newPassword")String newPassword){
		if(emailId!=null && emailId!="" && newPassword!=null && newPassword!="") {
			String passwordUpdate=repository.changePassword(emailId, newPassword);
			return new ResponseEntity<String>(passwordUpdate, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Please check the request....", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/getAllDepartment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LdapDepartments>> getDepartments() {
		List<LdapDepartments> getDepartments = repository.getAllDepartment();
		Collections.sort(getDepartments, comparing(LdapDepartments::getDepartmentName));
		return new ResponseEntity<List<LdapDepartments>>(getDepartments, HttpStatus.OK);

	}

	@GetMapping(path = "/getAllRoles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LdapRolse>> getAllRoles() {
		List<LdapRolse> getRoles = repository.getAllRoles();
		Collections.sort(getRoles, comparing(LdapRolse::getRoleNames));
		return new ResponseEntity<List<LdapRolse>>(getRoles, HttpStatus.OK);
	}
}
