package com.exception.common;

public class ControllerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ControllerException(String msg) {
        super(msg);
    }

    public ControllerException(String msg, Throwable t) {
        super(msg, t);
    }

    public ControllerException(Throwable t) {
        super(t);
    }
	
}
