package com.app.ttd.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupObject {

	private String uid;
	private String cn;
	private String sn;
	private String userPassword;
	private String mail;
	//private String desc;
	private String mobileNo;
}
