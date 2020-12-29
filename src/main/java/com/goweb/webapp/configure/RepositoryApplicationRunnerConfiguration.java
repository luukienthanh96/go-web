/**
 * 
 */
package com.goweb.webapp.configure;

import java.util.List;

import javax.persistence.Query;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.goweb.webapp.common.AbstractCommonClass;
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
	}
	
	private List<Metadata> innitTmetadata(){
		String sql = "Select tm.ID, tm.LOOKUPCODE, tm.LOOKUPCODEID, tm.LANG, tm.VALUE, tm.ORDERBY " +
				"from Metadata tm group by tm.LOOKUPCODE, tm.ORDERBY, tm.ID, tm.LOOKUPCODEID, tm.LANG, tm.VALUE order by tm.LOOKUPCODE,tm.ORDERBY, tm.VALUE";
		Query query = entityManager.createNativeQuery(sql, Metadata.class);
		return query.getResultList();
	}
}
