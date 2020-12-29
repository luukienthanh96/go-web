/**
 * 
 */
package com.goweb.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.goweb.webapp.common.AbstractCommonClass;

/**
 * @author Kraken
 *
 */
@Controller
public class LoginController extends AbstractCommonClass{

	@RequestMapping(value = { "/login", "/logout" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login";
	}
}
