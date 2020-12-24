/**
 * 
 */
package com.goweb.webapp.repository.service;

import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.repository.model.User;

/**
 * @author GVL00940
 *
 */
public interface UserManagerRepositoryService {

	public User getOneUserByUserName(String userName) throws BaseException;
	
}
