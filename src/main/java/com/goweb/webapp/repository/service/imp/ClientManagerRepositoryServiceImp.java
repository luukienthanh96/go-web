/**
 * 
 */
package com.goweb.webapp.repository.service.imp;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.repository.dao.ClientDao;
import com.goweb.webapp.repository.model.Client;
import com.goweb.webapp.repository.model.Metadata;
import com.goweb.webapp.repository.service.ClientManagerRepositoryService;

/**
 * @author GVL00940
 *
 */
@Service("clientManagerRepositoryService")
public class ClientManagerRepositoryServiceImp extends AbstractCommonClass
		implements ClientManagerRepositoryService {

	private ClientDao objectDao;

	@Autowired
	public ClientManagerRepositoryServiceImp(ClientDao objectDao) {
		this.objectDao = objectDao;
	}

	@Override
	public List<Client> getListClientByUserName(String userName) throws BaseException {
		String sql = "Select tc.* from Client tc Where tc.client_serve_user = :client_serve_user";
		Query query = entityManager.createNativeQuery(sql, Client.class);
		query.setParameter("client_serve_user", userName);
		return query.getResultList();
	}

	@Override
	public Client createNewClient(Client client) throws BaseException {
		return objectDao.save(client);
	}


}
