package com.estsoft.mysite.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.estsoft.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	@Autowired
	private SqlSession sqlSession;
	 
	 public int delete( GuestBookVo vo ) {
		 return sqlSession.delete("guestbook.delete", vo);
	   }
	
	 public Long insert(GuestBookVo vo) {  //vo 인풋을 받는다	
		int count = sqlSession.insert("guestbook.insert", vo);
		if( count == 0 ) {
			return 0L;
		}
		System.out.println( count +" :  "+ vo.getNo( ) );
		return vo.getNo();
	}

	public List<GuestBookVo> getList( ) {
		
		/*StopWatch stopWatch = new StopWatch();
		stopWatch.start();*/
		
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList");
		
		/*stopWatch.stop();
		System.out.println("[Execution Time] [GuestBookDao.getList] : "+stopWatch.getTotalTimeMillis( )+" millis");*/
		
		return list;
	}

	public GuestBookVo get( Long no ) {
		GuestBookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
	}	

	public List<GuestBookVo> getList( int page ) { 
		
		List<GuestBookVo> list = sqlSession.selectList( "guestbook.selectPageList", page );
		
		return list;
	}
}
