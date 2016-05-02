package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;

	public void insert( BoardVo boardVo) {
		sqlSession.insert("board.insert", boardVo);
	}

	public void insert1(BoardVo boardVo) {
		sqlSession.insert("board.insert1", boardVo);
	}
	
	public void delete(BoardVo boardVo) {
		sqlSession.delete("board.delete", boardVo);
	}

	public void update(BoardVo boardVo) {
		sqlSession.update("board.update", boardVo);
	}

	public BoardVo getBoard(Long no) {
		return sqlSession.selectOne( "board.getByNo", no);	
	}
	
	public int getSearchedCount(String wannaSearch) {
		Map<String, Object> map = new HashMap<String, Object>( );
		map.put("wannaSearch", wannaSearch);
		return sqlSession.selectOne("board.getTotalCount", map);
//		return sqlSession.selectOne("board.getTotalCount", wannaSearch);
	}

	public void plusView(Long no) {
		sqlSession.update("board.updateView", no);
	}

	public void setNewOrder(BoardVo boardVo) {
		sqlSession.update("board.updateOrder", boardVo);
	}
	
	public List<BoardVo> getSearchedPagingList(String wannaSearch, int currentPage, int rowSize) {
		Map<String, Object> map = new HashMap<String, Object>( );
		int rowFrom = (currentPage-1)*rowSize;
		map.put("wannaSearch", wannaSearch);
		map.put("rowFrom", rowFrom);
		map.put("rowSize", rowSize);
		/*
		List<BoardVo> list = sqlSession.selectList("board.selectSearchedPageList", map);
		for(BoardVo vo : list) {
			System.out.println(vo);
		}*/		
		return sqlSession.selectList("board.selectSearchedPageList", map);
	}

}