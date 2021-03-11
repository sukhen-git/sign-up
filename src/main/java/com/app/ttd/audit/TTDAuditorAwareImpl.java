package com.app.ttd.audit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.app.ttd.entity.UserDetailsEntity;
//import org.springframework.security.core.context.SecurityContextHolder;


public class TTDAuditorAwareImpl implements AuditorAware<String>{

	@Override
    public Optional<String> getCurrentAuditor() {
		
        //String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //return Optional.of(userName);
		return Optional.of("TTD User");
    }

}
