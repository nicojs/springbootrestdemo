package com.infosupport.nicojs.springbootrestdemo.rest;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id, String entityName) {
        super(String.format("%s with id %d not found.", entityName, id));
    }
}
