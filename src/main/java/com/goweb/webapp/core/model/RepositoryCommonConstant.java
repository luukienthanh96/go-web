/**
 * 
 */
package com.goweb.webapp.core.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.goweb.webapp.model.HomeMetadataChannel;
import com.goweb.webapp.repository.model.Metadata;

/**
 * @author GVL00940
 *
 */

@Component
public class RepositoryCommonConstant {

	private List<Metadata> listAllMetadata;
	private Map<String, List<Metadata>> hashmapMetadata;
	private List<HomeMetadataChannel> listChannelMetadata;

	/**
	 * @return the listAllMetadata
	 */
	public List<Metadata> getListAllMetadata() {
		return listAllMetadata;
	}

	/**
	 * @param listAllMetadata the listAllMetadata to set
	 */
	public void setListAllMetadata(List<Metadata> listAllMetadata) {
		this.listAllMetadata = listAllMetadata;
	}

	/**
	 * @return the hashmapMetadata
	 */
	public Map<String, List<Metadata>> getHashmapMetadata() {
		return hashmapMetadata;
	}

	/**
	 * @param hashmapMetadata the hashmapMetadata to set
	 */
	public void setHashmapMetadata(Map<String, List<Metadata>> hashmapMetadata) {
		this.hashmapMetadata = hashmapMetadata;
	}

	/**
	 * @return the listChannelMetadata
	 */
	public List<HomeMetadataChannel> getListChannelMetadata() {
		return listChannelMetadata;
	}

	/**
	 * @param listChannelMetadata the listChannelMetadata to set
	 */
	public void setListChannelMetadata(List<HomeMetadataChannel> listChannelMetadata) {
		this.listChannelMetadata = listChannelMetadata;
	}

}
