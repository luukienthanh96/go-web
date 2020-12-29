/**
 * 
 */
package com.goweb.webapp.configure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.core.model.GoConstant;
import com.goweb.webapp.model.HomeMetadataChannel;
import com.goweb.webapp.repository.model.Metadata;


/**
 * @author GVL00940
 *
 */
@Component
public class RepositoryApplicationRunnerConfiguration extends AbstractCommonClass implements ApplicationRunner {

	/* (non-Javadoc)
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		
		//Set list all metadata
		repositoryCommonConstant.setListAllMetadata(innitTmetadata());
		
		//Hash Metadata
		repositoryCommonConstant.setHashmapMetadata(hashMetadataAsMap());
		
		//Build metadata for channel
		repositoryCommonConstant.setListChannelMetadata(buildChannelMetadata());
	}
	
	private List<Metadata> innitTmetadata(){
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY "
				+ "from Metadata tm group by tm.LOOKUPCODE, tm.ORDERBY, tm.ID, tm.LOOKUPCODEID, tm.LANG, tm.VALUE "
				+ "order by tm.LOOKUPCODE,tm.ORDERBY, tm.VALUE";
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		return query.getResultList();
	}
	
	private Map<String, List<Metadata>> hashMetadataAsMap(){
		Map<String, List<Metadata>> maps = new HashMap<String, List<Metadata>>();
		if(CollectionUtils.isEmpty(repositoryCommonConstant.getListAllMetadata()))
		{
			innitTmetadata();
		}
		for(Metadata item:repositoryCommonConstant.getListAllMetadata())
		{
			List<Metadata> ls;
			if(!maps.containsKey(item.getLookupCode())) {
				ls = new ArrayList<Metadata>();
			}else {
				ls = maps.get(item.getLookupCode());
				maps.remove(item.getLookupCode());
			}
			ls.add(item);
			maps.put(item.getLookupCode(), ls);
		}
		return maps;
	}
	
	private List<HomeMetadataChannel> buildChannelMetadata(){
		List<HomeMetadataChannel> listChannelMetadata = new ArrayList<HomeMetadataChannel>();
		List<Metadata> lsDummy;
		if(CollectionUtils.isEmpty(repositoryCommonConstant.getHashmapMetadata()))
		{
			hashMetadataAsMap();
		}
		lsDummy = repositoryCommonConstant.getHashmapMetadata().get(GoConstant.METADATA_LOOKUP_CHANNEL);
		if(!CollectionUtils.isEmpty(lsDummy)){
			for(Metadata dummy :lsDummy)
			{
				HomeMetadataChannel item = gson.fromJson(gson.toJson(dummy), HomeMetadataChannel.class);
				if(listChannelMetadata.isEmpty())
				{
					listChannelMetadata.add(item);
				}
				else
				{
					boolean isFound = false;
					for(HomeMetadataChannel channel : listChannelMetadata)
					{
						if(item.getValue().equalsIgnoreCase(channel.getValue().trim()))
						{
							isFound = true;
							String str = channel.getLookupCodeId() + ", " +item.getLookupCodeId();
							channel.setLookupCodeId(str);
						}
					}
					if(!isFound)
					{
						listChannelMetadata.add(item);
					}
				}
			}
		}
		return listChannelMetadata;
	}
	
}
