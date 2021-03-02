package com.app.ttd.util;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

public class RoleContextMapper extends AbstractContextMapper<LdapRolse>{

	@Override
	protected LdapRolse doMapFromContext(DirContextOperations ctx) {
		LdapRolse getRoles=new LdapRolse();
		getRoles.setRoleNames(ctx.getStringAttribute("cn"));
		return getRoles;
	}

}
