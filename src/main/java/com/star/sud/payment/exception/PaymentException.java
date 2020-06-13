package com.star.sud.payment.exception;

import com.star.sud.order.exception.OrderException;

public class PaymentException extends OrderException  {

    private static final long serialVersionUID = -4582470401926451120L;

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable e) {
        super(message, e);
    }
}
