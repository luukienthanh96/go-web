/**
 * 
 */
package com.goweb.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.repository.model.Metadata;

/**
 * @author Kraken
 *
 */
@Controller
public class HomeController extends AbstractCommonClass{

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Home Page");
		List<Metadata> lst = repositoryCommonConstant.getListAllMetadata();
		for (Metadata item : lst){
			logger.info("lookupCodeId : " + item.getLookupCodeId());
			logger.info("value : " + item.getValue());
		}
		return "pages/home";
	}
}
