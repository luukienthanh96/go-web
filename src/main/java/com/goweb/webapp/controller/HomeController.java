/**
 * 
 */
package com.goweb.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goweb.webapp.common.AbstractCommonClass;
import com.goweb.webapp.model.HomeMetadataChannel;

/**
 * @author Kraken
 *
 */
@Controller
public class HomeController extends AbstractCommonClass{

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Home Page");
		List<HomeMetadataChannel> lst = repositoryCommonConstant.getListChannelMetadata();
		for (HomeMetadataChannel item : lst){
			logger.info("lookupCodeId : " + item.getLookupCodeId());
			logger.info("value : " + item.getValue());
		}
		model.addAttribute("lstHome", lst);
		return "pages/home";
	}
}
