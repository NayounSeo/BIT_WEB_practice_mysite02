package com.estsoft.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.annotation.Auth;
import com.estsoft.mysite.annotation.AuthUser;
import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 사실 @Auth 단 메소드는 NULL 체크 안해줘도 되지용 Annotation에서 체크합니다.
	@Auth
	@RequestMapping("/writeform")
	public String writeform( HttpSession session ) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if( authUser == null ) {
			return "redirect:/user/loginform";
		}
		return "/board/write";
	}
	
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String write( @AuthUser UserVo authUser, @ModelAttribute BoardVo vo) {
		vo.setUserNo(authUser.getNo( ) );
		boardService.writeBoard( vo );
		return "redirect:/board/list";
	}
	
	@RequestMapping( "/view/{no}")
	public String view( @PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.boardViewer(no);
		model.addAttribute("boardVo", boardVo);
		System.out.println("view에서 boardVo의 정보 : "+boardVo);
		return "/board/view";
	}
	
	@RequestMapping("/list")
	public String list( @RequestParam( value="wannaSearch", required=true, defaultValue="" ) String wannaSearch, @RequestParam( value="page", required=true, defaultValue="1") int page, Model model ) {
		Map<String, Object> map = boardService.listBoard( wannaSearch, page );
		model.addAttribute( "map", map );
		return "/board/list";
	}
	
	@Auth
	@RequestMapping("/modifyform/{no}")
	public String modifyform(@PathVariable("no") Long no, Model model ) {
		BoardVo boardVo = boardService.noSelectedBoard(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/modify";
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute BoardVo boardVo ) {
		boardService.modifyBoard(boardVo);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	@Auth
	@RequestMapping( "/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.deleteBoard(no);
		return "redirect:/board/list";
	}

	@Auth
	@RequestMapping("/replyform/{no}")
	public String replyform(@PathVariable("no") Long no, Model model ) {
		BoardVo boardVo = boardService.noSelectedBoard(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/reply";
	}
	
	@RequestMapping("/reply")
	public String reply(@ModelAttribute BoardVo boardVo) {
		boardService.writeReplyBoard(boardVo);
		return "redirect:/board/list";
	}

}
