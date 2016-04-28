package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	@Autowired
	private DataSource dataSource;

	public void insert( BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			// 이렇게 하면 max 관련한 다른 메소드나 static 변수는 필요 없다.
			String sql = "INSERT INTO board  VALUES (null, ?, ?, ?, now( ), 0, (SELECT ifnull(max(group_no), 0) + 1 FROM board as b), 1, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getUserNo());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());

			pstmt.executeUpdate();
			System.out.println("들어오기는 했나요? BoardDao의 insert");
			
		} catch (SQLException ex) {
			System.out.println("insert error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("insert error : " + ex);
			}
		}
	}

	public void delete(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();

			String sql = "DELETE FROM board WHERE user_no=? AND board_no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUserNo());
			pstmt.setLong(2, vo.getNo());

			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("delete error : " + ex);
			}
		}
	}

	public void update(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE board SET title=?, content=? WHERE board_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			pstmt.executeUpdate();
			
			System.out.println("update board에 들어왔습니다");
		} catch (SQLException ex) {
			System.out.println("error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("update error : " + ex);
			}
		}
	}

	public BoardVo getBoard(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT board_no, user_no, title, content, reg_date, views, group_no, order_no, depth FROM board WHERE board_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			BoardVo vo = new BoardVo();

			while (rs.next()) {
				Long userNo = rs.getLong(2);
				String title = rs.getString(3);
				String content = rs.getString(4);
				String regDate = rs.getString(5);
				int views = rs.getInt(6);
				int groupNo = rs.getInt(7);
				int orderNo = rs.getInt(8);
				int depth = rs.getInt(9);
				// 복붙이 문제였다...퓨퓨
				// System.out.println("여기는 Dao의 getBoard : depth는 "+depth);

				vo.setNo(no);
				vo.setUserNo(userNo);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setRegDate(regDate);
				vo.setViews(views);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
			}
			return vo;

		} catch (SQLException ex) {
			System.out.println("error : " + ex);
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("getBoard error : " + ex);
			}
		}
	}

	public void plusView(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE board SET views=views+1 WHERE board_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("plusView error : " + ex);
			}
		}
	}
	
	//원래 쓰던 메소드 1
/*
	public List<BoardVo> getSearchedList(String wannaSearch) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT board_no, user_no, title, reg_date, views, group_no, order_no, depth FROM board "
					+ "WHERE title LIKE ? OR content LIKE ? " + "ORDER BY group_no DESC, order_no ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + wannaSearch + "%");
			pstmt.setString(2, "%" + wannaSearch + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				Long userNo = rs.getLong(2);
				String title = rs.getString(3);
				String regDate = rs.getString(4);
				int views = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUserNo(userNo);
				vo.setTitle(title);
				vo.setRegDate(regDate);
				vo.setViews(views);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				list.add(vo);
			}
			return list;

		} catch (SQLException ex) {
			System.out.println("getSearchedList error : " + ex);
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("getSearchedList error : " + ex);
			}
		}
	}
*/

	//원래 쓰던 메소드 2
	/*
	public List<BoardVo> getList() {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT board_no, user_no, title, reg_date, views, group_no, order_no, depth FROM board ORDER BY group_no DESC, order_no ASC";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				Long userNo = rs.getLong(2);
				String title = rs.getString(3);
				String regDate = rs.getString(4);
				int views = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUserNo(userNo);
				vo.setTitle(title);
				vo.setRegDate(regDate);
				vo.setViews(views);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);

				list.add(vo);
			}
			return list;

		} catch (SQLException ex) {
			System.out.println("error : " + ex);
			return null;
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
				System.out.println("error : " + ex);
			}
		}
	}
*/

	// 계층형 게시판을 구현하다 겁이나서 메소드를 새로 파기로 한다. ㅎㅅㅎ
	public void insert1(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO board  VALUES (null, ?, ?, ?, now( ), 0, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getUserNo());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getGroupNo());
			pstmt.setInt(5, vo.getOrderNo());
			pstmt.setInt(6, vo.getDepth());
			System.out.println("여기는 DAO : 현 게시물의 순서 번호는 " + vo.getOrderNo());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("insert1 error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("insert1 error : " + ex);
			}
		}
	}

	public void setNewOrder(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE board SET order_no = order_no+1 WHERE group_no = ? AND order_no >= ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, vo.getGroupNo());
			pstmt.setInt(2, vo.getOrderNo());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("error : " + ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("setNewOrder error : " + ex);
			}
		}
	}

	public List<BoardVo> getSearchedPagingList(String wannaSearch, int currentPage, int rowSize) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT b.board_no, u.name, b.title, b.reg_date, b.views, b.group_no, b.order_no, b.depth, b.user_no FROM board b, user u "
					+ "WHERE b.user_no = u.no AND (title LIKE ? OR content LIKE ?) " 
					+ "ORDER BY group_no DESC, order_no ASC " 
					+ "LIMIT ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + wannaSearch + "%");
			pstmt.setString(2, "%" + wannaSearch + "%");
			pstmt.setInt(3, (currentPage - 1) * rowSize);
			pstmt.setInt(4, rowSize);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String userName = rs.getString(2);
//				System.out.println("getSearchedPagingList 에서의 userName "+userName);
				String title = rs.getString(3);
				String regDate = rs.getString(4);
				int views = rs.getInt(5);
				int groupNo = rs.getInt(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUserName(userName);
				vo.setTitle(title);
				vo.setRegDate(regDate);
				vo.setViews(views);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);

				list.add(vo);
			}
			return list;
		} catch (SQLException ex) {
			System.out.println("error : " + ex);
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("error : " + ex);
			}
		}
	}

	public int getSearchedCount(String wannaSearch) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM board " + "WHERE title LIKE ? OR content LIKE ? "
					+ "ORDER BY group_no DESC, order_no ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + wannaSearch + "%");
			pstmt.setString(2, "%" + wannaSearch + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (SQLException ex) {
			System.out.println("getSearchedCount의  error : " + ex);
			return 0;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println("error : " + ex);
			}
		}
	}
}
