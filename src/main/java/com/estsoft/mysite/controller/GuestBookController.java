package com.estsoft.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.estsoft.mysite.service.GuestBookService;
import com.estsoft.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guest")
public class GuestBookController {
	@Autowired
	private GuestBookService guestBookService;
	
	@RequestMapping("/index")
	public String index( Model model ) {
		List<GuestBookVo> list = guestBookService.getMessageList( );
		model.addAttribute("list", list);
		return "/guestbook/index";
	}
	
	@RequestMapping( value="/insert", method=RequestMethod.POST )
	public String insert(@ModelAttribute GuestBookVo vo) {
		guestBookService.insertMessage(vo);
		//아하 이러면 다시 Controller로 돌아온다.>!
		return "redirect:/guest/index";
	}
	
	@RequestMapping( value="/delete", method=RequestMethod.POST )
	public String delete(@ModelAttribute GuestBookVo vo ) {
		System.out.println("/delete에서의 "+vo);
		guestBookService.deleteMessage(vo);
		return "redirect:/guest/index";
	}
	
	@RequestMapping( "/deleteform/{no}")
	public String deleteform( @PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		System.out.println("/deleteform에서의 "+no);
		return "/guestbook/deleteform";
	}
	
	
	
}
