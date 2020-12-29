/**
 * 
 */
package com.goweb.webapp.repository.service;

import java.util.List;

import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.repository.model.Metadata;

/**
 * @author Kraken
 *
 */
public interface UtilityManagerRepositoryService {

	public List<Metadata> getAllMetadata() throws BaseException;
	
	public Metadata getMetadataById(String id) throws BaseException;
	
	public List<Metadata> getAllMetadataByLookupCode(String lookupCode) throws BaseException;
	
	public Metadata getMetadataByLookupCodeAndId(String lookupCode, String lookupId) throws BaseException;
}
