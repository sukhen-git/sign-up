package com.app.ttd.dto;

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


@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class UserDetails extends TTDJPAAuditable<String> implements Serializable{

	private static final long serialVersionUID = -6379293634166535149L;

	int id;
	
    @NotNull(message = "User_id Item Name not be null")
    @NotEmpty (message = "UserId Item Name not be Empty")
    @NotBlank(message = "UserId Item Name not be Blank")
	private String userId;
    

    @NotNull(message = "DepartmentName not be null")
    @NotEmpty (message = "DepartmentName not be Empty")
    @NotBlank(message = "DepartmentName not be Blank")
	private String departmentName;
	
   
    @NotNull(message = "CreatedBy not be null")
    @NotEmpty (message = "CreatedBy not be Empty")
    @NotBlank(message = "CreatedBy not be Blank")
	private String createdBy;
	
	
    @NotNull(message = "LastmodifiedBy not be null")
    @NotEmpty (message = "LastmodifiedBy not be Empty")
    @NotBlank(message = "LastmodifiedBy not be Blank")
	private String lastmodifiedBy;

    @NotNull(message = "DepartmentName not be null")
    private boolean activateUser;
}
