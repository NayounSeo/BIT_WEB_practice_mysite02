package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
/*	@Autowired
 *  private MailSender mailSender*/
	
	public void join( UserVo vo) {
		userDao.insert(vo);
		//메일 보내기를 여기에 넣는다고
	}
	
	public UserVo login( UserVo vo) {
		UserVo authUser = userDao.get( vo );
		return authUser;
	}
	
	public UserVo getUser ( String email ) {
		UserVo vo  = userDao.get( email );
		return vo;
	}
	
}
