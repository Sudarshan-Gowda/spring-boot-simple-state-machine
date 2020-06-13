package com.star.sud.payment.service;

import org.springframework.stereotype.Service;

import com.star.sud.event.ProcessData;
import com.star.sud.event.Processor;
import com.star.sud.order.OrderEvent;
import com.star.sud.order.dto.OrderData;
import com.star.sud.payment.exception.PaymentException;
import com.star.sud.process.exception.ProcessException;

@Service
public class PaymentProcessor implements Processor {
    @Override
    public ProcessData process(ProcessData data) throws ProcessException {
        if(((OrderData)data).getPayment() < 1.00) {
        	((OrderData)data).setEvent(OrderEvent.paymentError);
            throw new PaymentException(OrderEvent.paymentError.name());
        } else {
            ((OrderData)data).setEvent(OrderEvent.paymentSuccess);
        }
        return data;
    }
}
