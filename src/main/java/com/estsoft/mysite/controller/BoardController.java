package com.estsoft.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
	@RequestMapping("/index")
	public String list( ) {
		return "/board/index";
	}
	
	@RequestMapping("/writeform")
	public String writeform( ) {
		return "/board/write";
	}
	
	@RequestMapping("/write")
	public String write( ) {
		return "/board/index";
	}
	
	


}
