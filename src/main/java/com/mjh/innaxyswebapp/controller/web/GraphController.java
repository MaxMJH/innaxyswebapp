package com.mjh.innaxyswebapp.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class which represents the controller used to display pages, as well as access
 * models via their respective services. This controller in particular simply shows
 * a blank page where data will be obtained via the {@link com.mjh.innaxyswebapp.controller.api.GraphAPIController}
 * by using JS requests, rather than setting the graph on the server-side.
 * 
 * @author	MaxMJH - MaxHarrisMJH@gmail.com
 * @version 1.0
 * @since 	02-10-2023
 */
@Controller
public class GraphController {	
	/**
	 * Method used to return a blank index page. This page can be accessed via '/'.
	 * 
	 * @return String which represents the name of the 'index.jsp' page, so that it can be resolved and displayed.
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
