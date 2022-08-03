package com.tv.techbank.cqrs.core.exceptions;

public class AggregatorNotFoundException extends RuntimeException {
    public AggregatorNotFoundException(String message){
        super(message);
    }
}
