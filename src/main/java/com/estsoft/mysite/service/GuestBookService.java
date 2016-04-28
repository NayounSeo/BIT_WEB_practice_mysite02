package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.GuestBookDao;
import com.estsoft.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	@Autowired
	private GuestBookDao guestBookDao;
	
	public List<GuestBookVo> getMessageList() {
		return guestBookDao.getList();
	}
	
	public List<GuestBookVo> getMessageList( int page ) {
		return guestBookDao.getList(page);
	}
	
	public boolean deleteMessage( GuestBookVo vo ) {
		return guestBookDao.delete(vo) == 1;
	}

	public GuestBookVo insertMessage( GuestBookVo vo ) {
		Long no = guestBookDao.insert( vo );
		if( no == 0 ) {
			return null;
		}
		GuestBookVo guestBookVo = guestBookDao.get(no);
		return guestBookVo;
	}
}
