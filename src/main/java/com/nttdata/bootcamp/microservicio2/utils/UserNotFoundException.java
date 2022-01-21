package com.nttdata.bootcamp.microservicio2.utils;


public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5713584292717311040L;

    public UserNotFoundException(String s) {
        super(s);
    }
}