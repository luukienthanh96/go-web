/**
 * 
 */
package com.goweb.webapp.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.goweb.webapp.repository.service.ClientManagerRepositoryService;
import com.goweb.webapp.repository.service.UserManagerRepositoryService;
import com.goweb.webapp.repository.service.UtilityManagerRepositoryService;

/**
 * @author Kraken
 *
 */
@Service("repositoryManagerService")
public class RepositoryManagerService {

	@Autowired
	private UserManagerRepositoryService userManagerRepositoryService;
	
	@Autowired
	private ClientManagerRepositoryService clientManagerRepositoryService;

	@Autowired
	private UtilityManagerRepositoryService utilityManagerRepositoryService;
	
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

	/**
	 * @return the clientManagerRepositoryService
	 */
	public ClientManagerRepositoryService getClientManagerRepositoryService() {
		return clientManagerRepositoryService;
	}

	/**
	 * @param clientManagerRepositoryService the clientManagerRepositoryService to set
	 */
	public void setClientManagerRepositoryService(@Qualifier("clientManagerRepositoryService") 
			ClientManagerRepositoryService clientManagerRepositoryService) {
		this.clientManagerRepositoryService = clientManagerRepositoryService;
	}

	/**
	 * @return the utilityManagerRepositoryService
	 */
	public UtilityManagerRepositoryService getUtilityManagerRepositoryService() {
		return utilityManagerRepositoryService;
	}

	/**
	 * @param utilityManagerRepositoryService the utilityManagerRepositoryService to set
	 */
	public void setUtilityManagerRepositoryService(@Qualifier("utilityManagerRepositoryService") 
	UtilityManagerRepositoryService utilityManagerRepositoryService) {
		this.utilityManagerRepositoryService = utilityManagerRepositoryService;
	}
	
}
