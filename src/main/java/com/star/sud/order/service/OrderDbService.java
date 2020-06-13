package com.star.sud.order.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.star.sud.order.OrderState;

@Service
public class OrderDbService {
    
    private final ConcurrentHashMap<String, OrderState> states;
    
    public OrderDbService() {
        this.states = new ConcurrentHashMap<String, OrderState>();
    }

	public ConcurrentHashMap<String, OrderState> getStates() {
        return states;
    }
}
