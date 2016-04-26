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
/*	@Autowired
	private DBConnection dbConnection;*/
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	
	/*
	 public void delete( GuestBookVo vo ) {
		 int countDeleted = sqlSession.delete("guestbook.delete", vo);
		 return countDeleted;
		 
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         conn = dataSource.getConnection( );
	         //
	         String sql = "DELETE FROM guestbook WHERE no=? AND passwd= password(?)";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setLong(1, vo.getNo( ));
	         pstmt.setString(2, vo.getPasswd( ));
	         pstmt.executeUpdate( );

	      } catch (SQLException ex) {
	         System.out.println("Error" + ex);
	         ex.printStackTrace();
	      }
	   }*/
	 
	 public int delete( GuestBookVo vo ) {
		 int countDeleted = sqlSession.delete("guestbook.delete", vo);
		 return countDeleted;
		 /*
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         conn = dataSource.getConnection( );
	         //
	         String sql = "DELETE FROM guestbook WHERE no=? AND passwd= password(?)";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setLong(1, vo.getNo( ));
	         pstmt.setString(2, vo.getPasswd( ));
	         pstmt.executeUpdate( );

	      } catch (SQLException ex) {
	         System.out.println("Error" + ex);
	         ex.printStackTrace();
	      }*/
	   }

	 /*
	public void insert(GuestBookVo vo) {  //vo 인풋을 받는다
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();

//			String sql = "INSERT INTO guestbook VALUES (null, ?, NOW( ), ?, ?)";
			//암호화 시키는 목적이 있기 때문에 ㅋㅋㅋㅋ 추가해준다! 이것때문에 안됐구만;;
			String sql = "INSERT INTO guestbook VALUES (null, ?, NOW( ), ?, password(?))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName( ) );
			pstmt.setString(2, vo.getMessage( ) );
			pstmt.setString(3, vo.getPasswd( ) );
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	*/

	
	 public Long insert(GuestBookVo vo) {  //vo 인풋을 받는다	
		int count = sqlSession.insert("guestbook.insert", vo);
		System.out.println( count +" :  "+ vo.getNo( ) );
		return 0L;
	}

	public List<GuestBookVo> getList() //throws GuestBookFetchListException 
	/*RuntimeException  -- 으로 아래를 바꿔주면 이제 굳이 타 메소드에서 사용 안해도!
	 * 인지 여기를 바꿔주는 게 맞는건지ㅋㅋㅋㅋㅋㅋㅋ
	 * 														*/ { // DB에서 가져오는 메소드야!!
		//sqlSession.selectList("", arg1);
		List<GuestBookVo> list = sqlSession.selectList("guestbook.selectList");
		/*List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no, name, reg_date, message FROM guestbook ORDER BY reg_date DESC";
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
			throw new GuestBookFetchListException( );
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
		}*/
		return list;
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

	public GuestBookVo get( Long no ) {
		GuestBookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
		/*GuestBookVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT name, passwd, message, reg_date FROM guestbook WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no );
			
			rs = pstmt.executeQuery();
			if( rs.next( ) ) {
				String name = rs.getString(1);
				String password = rs.getString(2);
				String message= rs.getString(3);
				String regDate = rs.getString(4);
				
				vo = new GuestBookVo( );
				vo.setName(name);
				vo.setPasswd(password);
				vo.setMessage(message);
				vo.setRegDate(regDate);
			}
			return vo;
		} catch (SQLException e) {
			System.out.println("error : "+e);
			return null;
		} finally {
			try {
				if( rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}*/
	}	

}
