package com.app.ttd.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.app.ttd.audit.TTDJPAAuditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_dtls")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class UserDetailsEntity extends TTDJPAAuditable<String> implements Serializable{

	private static final long serialVersionUID = -6379293634166535149L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Size(max = 100)
    @Column(name = "user_id",nullable = false)
    @NotNull(message = "User_id Item Name not be null")
    @NotEmpty (message = "UserId Item Name not be Empty")
    @NotBlank(message = "UserId Item Name not be Blank")
	private String userId;
    
	@Size(max = 100)
    @Column(name = "createdby",nullable = false)
	@NotNull(message = "CreatedBy not be null")
    @NotEmpty (message = "CreatedBy not be Empty")
    @NotBlank(message = "CreatedBy not be Blank")
	private String createdBy;
	
	@Size(max = 100)
    @Column(name = "lastmodifiedby",nullable = false)
	@NotNull(message = "LastmodifiedBy not be null")
    @NotEmpty (message = "LastmodifiedBy not be Empty")
    @NotBlank(message = "LastmodifiedBy not be Blank")
	private String lastmodifiedBy;
    
    @Size(max = 100)
    @Column(name = "department_name",nullable = false)
    @NotNull(message = "DepartmentName not be null")
    @NotEmpty (message = "DepartmentName not be Empty")
    @NotBlank(message = "DepartmentName not be Blank")
	private String departmentName;
	
    @Column(name = "active_user",nullable = false)
    @NotNull(message = "DepartmentName not be null")
    private boolean activateUser;
}
