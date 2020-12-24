/**
 * 
 */
package com.goweb.webapp.repository.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.repository.dao.UserDao;
import com.goweb.webapp.repository.model.User;
import com.goweb.webapp.repository.service.UserManagerRepositoryService;

/**
 * @author GVL00940
 *
 */
@Service("userManagerRepositoryService")
public class UserManagerRepositoryServiceImp extends AbstractCommonClass
		implements UserManagerRepositoryService {

	private UserDao objectDao;

	@Autowired
	public UserManagerRepositoryServiceImp(UserDao objectDao) {
		this.objectDao = objectDao;
	}

	/* (non-Javadoc)
	 * @see com.goweb.webapp.repository.service.UserManagerRepositoryService#getOneUserByUserName(java.lang.String)
	 */
	@Override
	public User getOneUserByUserName(String userName) throws BaseException {
		return objectDao.getOne(userName);
	}

}
