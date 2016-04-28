package com.estsoft.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private static final int row_Size = 5;
	private static final int page_Size=3;
	@Autowired
	private BoardDao dao;
	
	@RequestMapping("/list")
	public String list( @RequestParam( value="wannaSearch", required=false) String wannaSearch, @RequestParam( value="page", required=false) int page, Model model ) {
		// 변수 처리 안함
		int currentPage = 1;
		if ( page != 1) {
			currentPage = page;
		}
		int totalBoards = dao.getSearchedCount(wannaSearch);
		int totalPage = (int)Math.ceil( (double) totalBoards / row_Size);
		if( currentPage < 1 || currentPage > totalPage ) {
			currentPage = 1;
		}
		int firstPage = (int)(Math.ceil( (double) currentPage/ page_Size) -1) * page_Size + 1;
		if (firstPage < 0) {
			firstPage = 1;
		}
		int lastPage = firstPage + page_Size - 1;
		if ( totalPage < lastPage ) {
			lastPage = totalPage;
		}
		int prevPage = 0;
		if ( firstPage > page_Size ) {
			prevPage = firstPage-1;
		}
		int nextPage = 0;
		if ( lastPage < totalPage ) {
			nextPage = lastPage + 1;
		}
		List<BoardVo> list = dao.getSearchedPagingList(wannaSearch, currentPage, row_Size);
		model.addAttribute("wannaSearch", wannaSearch);
		model.addAttribute("page", "1");
		model.addAttribute("rowSize", row_Size);
		model.addAttribute("pageSize", page_Size);
		model.addAttribute("totalBoards", totalBoards);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@RequestMapping("/writeform")
	public String writeform( HttpSession session ) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if( authUser == null ) {
			return "redirect:/user/loginform";
		}
		return "/board/write";
	}
	
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String write( @ModelAttribute BoardVo vo, HttpSession session) {
		System.out.println(vo);
		/*
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		vo.setUserNo(authUser.getNo());
		vo.setUserName(authUser.getName());
		System.out.println("write에서의 authUser는 " + authUser);
		*/
		

		
		dao.insert(vo);
		return "redirect:/board/list";
	}

	@RequestMapping( "/view/{no}")
	public String view( @PathVariable("no") Long no, Model model) {
		BoardVo boardVo = dao.getBoard(no);
		dao.plusView(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/view";
	}
	
	@RequestMapping("/modifyform/{no}")
	public String modifyform(@PathVariable("no") Long no, Model model ) {
		BoardVo boardVo = dao.getBoard(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/modify";
	}
	
	@RequestMapping("/modify")
	public String modify(@ModelAttribute BoardVo boardVo ) {
		dao.update(boardVo);
		System.out.println("/modify에서 "+boardVo);
		return "redirect:/board/view/"+boardVo.getNo();
	}
	
	@RequestMapping( "/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		BoardVo vo = dao.getBoard(no);
		System.out.println("/deleteform에서의 "+vo);
		dao.delete(vo);
		return "redirect:/board/list";
	}
	
	@RequestMapping("/replyform/{no}")
	public String replyform(@PathVariable("no") Long no, Model model ) {
		System.out.println("/replyform 여기까지 들어왔어요!");
		BoardVo vo = dao.getBoard(no);
		model.addAttribute("boardVo", vo);
		System.out.println(vo);
		return "/board/reply";
	}
	
	@RequestMapping("/reply")
	public String reply(@ModelAttribute BoardVo boardVo ) {
		dao.setNewOrder(boardVo);
		dao.insert1(boardVo);
		return "redirect:/board/list";
	}

}
