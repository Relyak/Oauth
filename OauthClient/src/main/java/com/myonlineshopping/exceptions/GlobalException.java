package com.myonlineshopping.exceptions;

public class GlobalException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public GlobalException() {
        super("GlobalException");
    }

    public GlobalException(String message) {
        super(message);
    }
    public GlobalException(Long id) {
        super("GlobalException for element with id: " + id);
    }

    public long getId(){
        return this.serialVersionUID;
    }

}
