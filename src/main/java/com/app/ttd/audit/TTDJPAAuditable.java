package com.app.ttd.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class TTDJPAAuditable<T> implements Serializable{
	

	private static final long serialVersionUID = 1L;

	//@CreatedBy
    //protected T createdby;
    @Version
    private int version;
    @CreatedDate
    protected Date createddate;
    //@LastModifiedBy
    //protected T lastmodifiedby;
    @LastModifiedDate
    protected Date lastmodifieddate;

}
