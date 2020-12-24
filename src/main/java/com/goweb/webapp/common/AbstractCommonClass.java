/**
 * 
 */
package com.goweb.webapp.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;

/**
 * @author Kraken
 *
 */
public abstract class AbstractCommonClass {

	@PersistenceContext(unitName = "entityManagerSqlServer")
	public EntityManager entityManager;
	
	@Autowired
	public Environment env;
	
	@Autowired
	public RepositoryManagerService repositoryManagerService;

	/**
	 * @return the repositoryManagerService
	 */
	public RepositoryManagerService getRepositoryManagerService() {
		return repositoryManagerService;
	}

	/**
	 * @param repositoryManagerService the repositoryManagerService to set
	 */
	public void setRepositoryManagerService(@Qualifier("repositoryManagerService") RepositoryManagerService repositoryManagerService) {
		this.repositoryManagerService = repositoryManagerService;
	}
	
}
