package com.estsoft.mysite.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {
	@Autowired
	private SqlSession sqlSession;

	public void insert(UserVo userVo) {
		sqlSession.update("user.insert", userVo);
	}
	
	public UserVo get(UserVo userVo) {
		/*UserVo vo = sqlSession.selectOne("user.selectByNo", userNo);
		return vo;*/
		return sqlSession.selectOne("user.selectByEmailAndPassword", userVo);
	}
	
	public UserVo get(Long userNo) {
		return sqlSession.selectOne("user.selectByNo", userNo);
	}
	
	public void update( UserVo userVo ) {
		sqlSession.update("user.update", userVo);
	}

	public UserVo get( String email ) {
		return sqlSession.selectOne("user.selectByEmail", email );		
	}
	
}

