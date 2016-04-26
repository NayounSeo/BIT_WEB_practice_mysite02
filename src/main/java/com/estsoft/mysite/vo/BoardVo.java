package com.estsoft.mysite.vo;

public class BoardVo {
	private Long no;
	private Long userNo;
	private String title;
	private String content;
	private String regDate;
	private int views;
	private int groupNo;
	private int orderNo;
	private int depth;
	private String userName;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int count) {
		this.views = count;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", userNo=" + userNo + ", title=" + title + ", content=" + content + ", regDate="
				+ regDate + ", views=" + views + ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth
				+ ", userName=" + userName + "]";
	}
}
