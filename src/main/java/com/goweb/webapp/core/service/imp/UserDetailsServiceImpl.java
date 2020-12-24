/**
 * 
 */
package com.goweb.webapp.core.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.repository.dao.UserDao;
import com.goweb.webapp.repository.model.User;
import com.goweb.webapp.repository.model.UserRole;

/**
 * @author Kraken
 *
 */
@Service
public class UserDetailsServiceImpl extends AbstractCommonClass implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) {
		
		User user = userDao.getOne(userName);
		if (user == null || user.getUsername() == null|| StringUtils.isBlank(user.getUsername())) {
			System.out.println("User not found! " + userName);
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}
		
		// [ROLE_USER, ROLE_ADMIN,..]
		
		List<UserRole> roleNames = user.getUserRoles();
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if (roleNames != null && roleNames.size() > 0) {
			for (UserRole role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
				grantList.add(authority);
				System.out.print("Add role : " + role.getRole());
			}
		}
		
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);
		
		return userDetails;
	}

}
