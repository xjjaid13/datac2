package com.exception.common;

public class TipException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TipException(String msg) {
        super(msg);
    }

    public TipException(String msg, Throwable t) {
        super(msg, t);
    }

    public TipException(Throwable t) {
        super(t);
    }
	
}
