package com.gmarket.api.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFountException extends RuntimeException{

    public EntityNotFountException(String msg) {
        super(msg);
    }
}
