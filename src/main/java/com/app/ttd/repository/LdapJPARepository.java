package com.app.ttd.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.ttd.entity.UserDetailsEntity;


@Repository
public interface LdapJPARepository extends JpaRepository<UserDetailsEntity, Integer> {
	
	
	public UserDetailsEntity findByUserId(String userId);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserDetailsEntity SET activateUser = :activateUser WHERE userId =:userId")
	@Transactional
	public void activateUsers(@Param("userId") String userId,@Param("activateUser") boolean activateUser);
	
	@Query("SELECT u FROM  UserDetailsEntity u WHERE u.activateUser = 0")
	public List<UserDetailsEntity> getInactiveUsers();
}
