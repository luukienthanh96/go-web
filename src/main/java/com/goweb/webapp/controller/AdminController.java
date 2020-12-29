/**
 * 
 */
package com.goweb.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goweb.webapp.common.AbstractCommonClass;

/**
 * @author Kraken
 *
 */
@Controller
public class AdminController extends AbstractCommonClass{

	@RequestMapping("/admin")
	public String admin() {
		logger.info("Admin Page");
		return "admin/admin";
	}
}
