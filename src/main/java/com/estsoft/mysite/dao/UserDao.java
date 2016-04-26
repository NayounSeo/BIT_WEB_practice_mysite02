package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private DBConnection dbConnection;
	
	//메소드 이름 웬만하면  joinUser 이렇게 쓰지 마.. 다른 데 써야 해.. 
	//DB랑 연동되는 거니까 가능한 한 그냥 insert 이렇게 만들어주라고 하셨다
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dbConnection.getConnection();
			String sql = "INSERT INTO user VALUES(null, ?, ? , password(?), ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error : " +e);
		} finally {
			try {
				if ( pstmt != null) {
					pstmt.close();
				}
				if ( conn != null) {
					conn.close();
				}				
			} catch (SQLException ex) {
				System.out.println("error : " + ex);
			}
		}
	}
	
	// 보안 =  인증 + 권한
	
	// Authentication (Login) - 인증 처리에 필요한 메소드
	public UserVo get(UserVo vo) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT no, name, email FROM user WHERE email=? AND passwd=password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();
			if( rs.next( ) ) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);			
				userVo = new UserVo();	
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);		
			}
			return userVo;		
		} catch (SQLException e) {
			System.out.println("error : " +e);
			return null;		
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if ( pstmt != null) {
					pstmt.close();
				}
				if ( conn != null) {
					conn.close();
				}				
			} catch (SQLException ex) {
				System.out.println("error : " + ex);
			}
		}				
	}
	
	public UserVo get(Long userNo) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT no, name, email, gender FROM user WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, userNo );
			
			rs = pstmt.executeQuery();
			if( rs.next( ) ) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			return userVo;
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
		}
	}
	
	public void update( UserVo userVo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			if( "".equals( userVo.getPassword() ) ) {
				String sql = "UPDATE user SET  name=?, email=?, gender=? WHERE no = ?";
				pstmt = conn.prepareStatement( sql );
				
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getEmail() );
				pstmt.setString( 3, userVo.getGender() );
				pstmt.setLong(4, userVo.getNo());
				
			} else {
				String sql = "UPDATE user SET  name=?, email=?, passwd=password(?), gender=? WHERE no = ?";
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getEmail() );
				pstmt.setString(3, userVo.getPassword());
				pstmt.setString( 4, userVo.getGender() );
				pstmt.setLong(5, userVo.getNo());
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );	
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	public UserVo get( String email ) {
		UserVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection( );
			String sql = "SELECT no, email FROM user WHERE email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  email);
			rs = pstmt.executeQuery( );
			if( rs.next( )) {
				vo  = new UserVo();
				vo.setNo(rs.getLong(1));
				vo.setEmail(rs.getString(2));
			}
			System.out.println("dao에서의 vo : "+vo);
			return vo;
		} catch ( SQLException ex) {
			System.out.println("get Email error : "+ex );
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
		}			
	}
}

