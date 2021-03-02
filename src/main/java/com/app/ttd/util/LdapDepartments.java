package com.app.ttd.util;

import java.io.Serializable;
import java.util.Set;

import javax.naming.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapDepartments  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5017059929924132026L;
	private String departmentName;
}
