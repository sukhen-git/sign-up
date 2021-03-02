package com.app.ttd.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.app.ttd.util.DepartmentContextMapper;
import com.app.ttd.util.LdapDepartments;
import com.app.ttd.util.LdapRolse;
import com.app.ttd.util.RoleContextMapper;
import com.app.ttd.util.SignupObject;

@Service
public class LdapSignupRepository {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private LdapTemplate ldapTemplate;

	// private LdapName baseLdapPath;

	// public void setBaseLdapPath(LdapName baseLdapPath) {
	// this.baseLdapPath = baseLdapPath;
	// }
	public List<LdapRolse> getAllRoles() {
		return ldapTemplate.search(query().where("objectclass").is("groupOfUniqueNames"), new RoleContextMapper());
	}
	
	public List<LdapDepartments> getAllDepartment(){
		return ldapTemplate.search(query().where("objectclass").is("organizationalUnit"), new DepartmentContextMapper());
	}
	
	public List<String> getUID(String uid) {
		log.info("TtdLdapRepository Repository");
		LdapQuery query = query().base("ou=people").attributes("cn", "sn", "uid").where("objectclass").is("person")
				.and("uid").is(uid);

		log.info("Query ::- " + query.base().toString());

		return ldapTemplate.search(query, new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attrs) throws NamingException {
				return (String) attrs.get("uid").get();
			}
		});
	}

	public void createUser(SignupObject obj) {
		Name dn = LdapNameBuilder.newInstance().add("ou", "people").add("uid", obj.getUid()).build();
		DirContextAdapter context = new DirContextAdapter(dn);
		context.setAttributeValues("objectclass",
				new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" });
		context.setAttributeValue("cn", obj.getCn());
		context.setAttributeValue("sn", obj.getSn());
		context.setAttributeValue("mail", obj.getMail());
		context.setAttributeValue("mobile", obj.getMobileNo());
		context.setAttributeValue("uid", obj.getUid());
		// context.setAttributeValue("userPassword",Base64.getEncoder().encodeToString(obj.getUserPassword().getBytes()));
		context.setAttributeValue("userPassword", BCrypt.hashpw(obj.getUserPassword(), BCrypt.gensalt(4)));

		ldapTemplate.bind(context);
	}

	public void addMemberToGroup(String groupName, String role, SignupObject p) {
		Name groupDn = buildGroupDn(groupName, role);
		Name personDn = buildPersonDn(p);

		DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
		ctx.addAttributeValue("uniqueMember", personDn);

		ldapTemplate.modifyAttributes(ctx);
	}

	private Name buildGroupDn(String groupName, String role) {
		return LdapNameBuilder.newInstance().add("ou", "groups").add("ou", groupName).add("cn", role).build();
	}

	private Name buildPersonDn(SignupObject obj) {
		return LdapNameBuilder.newInstance().add("dc", "com").add("dc", "ttd").add("ou", "people")
				.add("uid", obj.getUid()).build();
	}

	public String changePassword(String emailId, String newPassword) {
		Attribute attr = new BasicAttribute("userPassword", BCrypt.hashpw(newPassword, BCrypt.gensalt(4)));
		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
		Name dn = buildDn(emailId);
		ldapTemplate.modifyAttributes(dn, new ModificationItem[] { item });
		return " Password Updated Successfully";

	}

	public Name buildDn(String emailId) {
		return LdapNameBuilder.newInstance()
				// .add("dc","com")
				// .add("dc","ttd")
				.add("ou", "people").add("uid", emailId).build();
	}

	private Attributes buildAttributes(String pwd) {
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("person");
		Attributes attrs = new BasicAttributes();
		attrs.put(ocattr);
		attrs.put("userPassword", BCrypt.hashpw(pwd, BCrypt.gensalt(4)));
		return attrs;
	}
}
