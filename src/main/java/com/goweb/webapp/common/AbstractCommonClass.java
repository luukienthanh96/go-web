/**
 * 
 */
package com.goweb.webapp.common;

import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.goweb.webapp.core.model.RepositoryCommonConstant;

/**
 * @author Kraken
 *
 */
public abstract class AbstractCommonClass {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	public static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@PersistenceContext(unitName = "entityManagerSqlServer")
	public EntityManager entityManager;
	
	@Autowired
	public Environment env;
	
	@Autowired
	public RepositoryManagerService repositoryManagerService;
	
	@Autowired
	public RepositoryCommonConstant repositoryCommonConstant;

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
