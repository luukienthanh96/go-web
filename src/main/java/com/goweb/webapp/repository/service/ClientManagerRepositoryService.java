/**
 * 
 */
package com.goweb.webapp.repository.service;

import java.util.List;

import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.repository.model.Client;

/**
 * @author GVL00940
 *
 */
public interface ClientManagerRepositoryService {

	public List<Client> getListClientByUserName(String userName) throws BaseException;
	
	public Client createNewClient(Client client) throws BaseException;
}
