package com.estsoft.mysite.exception;

@SuppressWarnings("serial")
public class GuestBookFetchListException extends Exception {
	public GuestBookFetchListException(String msg) {
		super( msg );
	}
	
	public GuestBookFetchListException( ) {
		super( "Exception occurs: getting guestbook list");
	}

}
