package com.star.sud.order.service;

import org.springframework.stereotype.Service;

import com.star.sud.event.ProcessData;
import com.star.sud.event.Processor;
import com.star.sud.order.OrderEvent;
import com.star.sud.order.dto.OrderData;
import com.star.sud.process.exception.ProcessException;

@Service
public class OrderProcessor implements Processor {
    @Override
    public ProcessData process(ProcessData data) throws ProcessException{   
        ((OrderData)data).setEvent(OrderEvent.orderCreated); 
        return data;
    }
}
