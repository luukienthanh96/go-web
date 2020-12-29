/**
 * 
 */
package com.goweb.webapp.repository.service.imp;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.core.model.exception.ServiceInvalidAgurmentException;
import com.goweb.webapp.repository.model.Metadata;
import com.goweb.webapp.repository.service.UtilityManagerRepositoryService;

/**
 * @author Kraken
 *
 */
@Service("utilityManagerRepositoryService")
public class UtilityManagerRepositoryServiceImp extends AbstractCommonClass implements UtilityManagerRepositoryService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goweb.webapp.repository.service.UtilityManagerRepositoryService#
	 * getAllMetadata()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Metadata> getAllMetadata() throws BaseException {
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY " +
							"from Metadata tm group by tm.LOOKUPCODE, tm.ORDERBY, tm.ID, tm.LOOKUPCODEID, tm.LANG, tm.VALUE order by tm.LOOKUPCODE,tm.ORDERBY, tm.VALUE";
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		return query.getResultList();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goweb.webapp.repository.service.UtilityManagerRepositoryService#
	 * getMetadataById(java.lang.String)
	 */
	@Override
	public Metadata getMetadataById(String id) throws BaseException {
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY from Metadata tm where tm.ID = :ID";
		
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		query.setParameter("ID", id);
		
		@SuppressWarnings("unchecked")
		List<Metadata> list = query.getResultList();

		if (list == null || list.size() == 0) {
			throw new ServiceInvalidAgurmentException(
					String.format(env.getProperty("MSG_007"), "Metadata", id));
		}
		return (Metadata) list.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goweb.webapp.repository.service.UtilityManagerRepositoryService#
	 * getAllMetadataByLookupCode(java.lang.String)
	 */
	@Override
	public List<Metadata> getAllMetadataByLookupCode(String lookupCode) throws BaseException {
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY from Metadata tm " +
							"where tm.LOOKUPCODE = :LOOKUPCODE group by tm.LOOKUPCODE, tm.ORDERBY, tm.ID, tm.LOOKUPCODEID, tm.LANG, tm.VALUE order by tm.LOOKUPCODE,tm.ORDERBY, tm.VALUE";
		
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		query.setParameter("LOOKUPCODE", lookupCode);
		
		@SuppressWarnings("unchecked")
		List<Metadata> list = query.getResultList();

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.goweb.webapp.repository.service.UtilityManagerRepositoryService#
	 * getMetadataByLookupCodeAndId(java.lang.String, java.lang.String)
	 */
	@Override
	public Metadata getMetadataByLookupCodeAndId(String lookupCode, String lookupId) throws BaseException {
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY from METADATA tm" +
							"where tm.LOOKUPCODE = :LOOKUPCODE and tm.LOOKUPCODEID = :LOOKUPCODEID ";
		
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		query.setParameter("LOOKUPCODE", lookupCode);
		query.setParameter("LOOKUPCODEID", lookupId);
		
		@SuppressWarnings("unchecked")
		List<Metadata> list = query.getResultList();

		if (list == null || list.size() == 0) {
			throw new ServiceInvalidAgurmentException(
					String.format(env.getProperty("MSG_007"), lookupCode, lookupId));
		}
		return (Metadata) list.get(0);
	}

}
