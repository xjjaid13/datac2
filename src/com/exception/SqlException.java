package com.exception;

public class SqlException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SqlException(String msg) {
        super(msg);
    }

    public SqlException(String msg, Throwable t) {
        super(msg, t);
    }

    public SqlException(Throwable t) {
        super(t);
    }
	
}
