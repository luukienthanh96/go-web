/**
 * 
 */
package com.goweb.webapp.core.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.goweb.webapp.repository.model.Metadata;

/**
 * @author GVL00940
 *
 */

@Component
public class RepositoryCommonConstant {

	private List<Metadata> listAllMetadata;

	/**
	 * @return the listAllMetadata
	 */
	public List<Metadata> getListAllMetadata() {
		return listAllMetadata;
	}

	/**
	 * @param listAllMetadata
	 *            the listAllMetadata to set
	 */
	public void setListAllMetadata(List<Metadata> listAllMetadata) {
		this.listAllMetadata = listAllMetadata;
	}

}
