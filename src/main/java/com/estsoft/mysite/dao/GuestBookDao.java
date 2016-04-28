package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.exception.GuestBookFetchListException;
import com.estsoft.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	@Autowired
	private SqlSession sqlSession;
	 
	 public int delete( GuestBookVo vo ) {
		 int countDeleted = sqlSession.delete("guestbook.delete", vo);
		 return countDeleted;
	   }
	
	 public Long insert(GuestBookVo vo) {  //vo 인풋을 받는다	
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println( count +" :  "+ vo.getNo( ) );
		return vo.getNo();
	}

	public List<GuestBookVo> getList( ) {
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList");
		return list;
	}

	public GuestBookVo get( Long no ) {
		GuestBookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
	}	
	
	public List<GuestBookVo> getList( int page ) { // DB에서 가져오는 메소드야!!
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no, name, reg_date, message "
					+ "FROM guestbook ORDER BY reg_date DESC "
					+ "LIMIT "+ (page-1)*5 +", 5";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String regDate = rs.getString(3);
				String message = rs.getString(4);

				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setRegDate(regDate);
				vo.setMessage(message);
				list.add(vo);
			}

		} catch (SQLException ex) {
			System.out.println("error : " + ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				} 
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}

	

}
