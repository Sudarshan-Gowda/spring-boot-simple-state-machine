package com.star.sud.order.exception;

import com.star.sud.process.exception.ProcessException;

public class OrderException extends ProcessException {

    private static final long serialVersionUID = 5587859227419203629L;
    
    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable e) {
        super(message, e);
    }
}
