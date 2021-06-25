package com.our.ourmbti.controller.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	
	//유저 메인 화면 컨트롤러
	@GetMapping(value = "/")
	public String home() {	
		return "/main/main";
	}
}
