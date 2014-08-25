package com.exception.common;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public ServiceException(Throwable t) {
        super(t);
    }
	
}