package com.app.ttd.util;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

public class DepartmentContextMapper extends AbstractContextMapper<LdapDepartments>{

	@Override
	protected LdapDepartments doMapFromContext(DirContextOperations ctx) {
		
		LdapDepartments getDetpList=new LdapDepartments();
		getDetpList.setDepartmentName(ctx.getStringAttribute("ou"));
		return getDetpList;
	}

}
