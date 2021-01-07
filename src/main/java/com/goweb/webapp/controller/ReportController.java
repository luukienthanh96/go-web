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
public class ReportController extends AbstractCommonClass{

	@RequestMapping(value = { "/bets" }, method = RequestMethod.GET)
	public String betPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("Bet Page");
		
		return "pages/betPage";
	}
}
