/**
 * 
 */
package com.goweb.webapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.goweb.webapp.repository.service.UserManagerRepositoryService;

/**
 * @author Kraken
 *
 */
@Service("repositoryManagerService")
public class RepositoryManagerService {

	@Autowired
	private UserManagerRepositoryService userManagerRepositoryService;

	/**
	 * @return the userManagerRepositoryService
	 */
	public UserManagerRepositoryService getUserManagerRepositoryService() {
		return userManagerRepositoryService;
	}

	/**
	 * @param userManagerRepositoryService
	 *            the userManagerRepositoryService to set
	 */
	public void setUserManagerRepositoryService(
			@Qualifier("userManagerRepositoryService") UserManagerRepositoryService userManagerRepositoryService) {
		this.userManagerRepositoryService = userManagerRepositoryService;
	}
}
