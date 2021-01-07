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
import com.goweb.webapp.core.model.exception.BaseException;
import com.goweb.webapp.core.util.DateUtils;
import com.goweb.webapp.repository.model.Client;

/**
 * @author Kraken
 *
 */
@Controller
public class ClientController extends AbstractCommonClass{

	@RequestMapping(value = { "/clients" }, method = RequestMethod.GET)
	public String clientPage(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		logger.info("Client Page");
		List<Client> lst = repositoryManagerService.getClientManagerRepositoryService()
				.getListClientByUserName(request.getUserPrincipal().getName());
		model.addAttribute("lstClient", lst);
		
		return "pages/client";
	}
	
	@RequestMapping(value = { "/addClients" }, method = RequestMethod.GET)
	public String addClient(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		logger.info("Client addClient Page");
		String userName = request.getUserPrincipal().getName();
		
		//Add New Client
		Client item = new Client(userName);
		item.setFullname(DateUtils.getSystemTimeStr());
		repositoryManagerService.getClientManagerRepositoryService().createNewClient(item);
		
		return "redirect:/clients";
	}
	
}
