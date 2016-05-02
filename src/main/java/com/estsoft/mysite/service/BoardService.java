package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int row_Size = 5;
	private static final int page_Size=3;
	
	@Autowired
	private BoardDao boardDao;
	
	public void writeBoard( BoardVo boardVo ){
		boardDao.insert( boardVo );
	}
	
	public BoardVo boardViewer(Long no) {
		boardDao.plusView(no);
		return boardDao.getBoard(no);
	}
	
	public BoardVo noSelectedBoard(Long no) {
		return boardDao.getBoard(no);
	}
	
	public Map<String, Object> listBoard( String wannaSearch, int page ) {
		int currentPage = 1;
		if ( page != 1) {
			currentPage = page;
		}
		System.out.println(wannaSearch);
		int totalBoards = boardDao.getSearchedCount(wannaSearch);
		
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
		List<BoardVo> list = boardDao.getSearchedPagingList(wannaSearch, currentPage, row_Size);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wannaSearch", wannaSearch);
		map.put("page", "1");
		map.put("rowSize", row_Size);
		map.put("pageSize", page_Size);
		map.put("totalBoards", totalBoards);
		map.put("currentPage", currentPage);
		map.put("firstPage", firstPage);
		map.put("lastPage", lastPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("list", list);
		
		return map;
	}

	public void modifyBoard( BoardVo boardVo ) {
		boardDao.update(boardVo);
	}
	
	public void deleteBoard(Long no) {
		BoardVo boardVo = boardDao.getBoard(no);
		boardDao.delete(boardVo);
	}
	
	public void writeReplyBoard(BoardVo boardVo) {
		boardVo.setOrderNo(boardVo.getOrderNo()+1);
		boardVo.setDepth(boardVo.getDepth()+1);
		boardDao.setNewOrder(boardVo);
		boardDao.insert1(boardVo);
	}
	
	
}
