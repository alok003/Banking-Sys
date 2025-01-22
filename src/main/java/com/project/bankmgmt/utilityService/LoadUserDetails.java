package com.project.bankmgmt.utilityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.bankmgmt.dao.UserDao;
import com.project.bankmgmt.entity.UserInfo;

@Component
public class LoadUserDetails implements UserDetailsService {

	@Autowired
	private UserDao userdao;
	private Logger log=LoggerFactory.getLogger(LoadUserDetails.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user = userdao.findByUserEmail(username);
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserEmail())
                    .password(user.getUserPassword())
                    .build();
        }
        log.error("Email not found {} ",username);
        throw new UsernameNotFoundException("User not found with Email: " + username);
	}

}
