package com.star.sud.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.star.sud.order.OrderEvent;
import com.star.sud.order.dto.OrderData;
import com.star.sud.order.exception.OrderException;
import com.star.sud.order.service.OrderStateTransitionsManager;
import com.star.sud.process.exception.ProcessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class OrderController {

	@Autowired
	private OrderStateTransitionsManager stateTrasitionsManager;

	@GetMapping("/order/cart")
	public String handleOrderPayment(@RequestParam double payment, @RequestParam String orderId) throws Exception {

		OrderData data = new OrderData();
		data.setPayment(payment);
		data.setOrderId(orderId);
		data.setEvent(OrderEvent.pay);
		data = (OrderData) stateTrasitionsManager.processEvent(data);

		return ((OrderEvent) data.getEvent()).name();
	}

	@ExceptionHandler(value = OrderException.class)
	public String handleOrderException(OrderException e) {
		return e.getMessage();
	}

	@GetMapping("/order")
	public String handleOrderSubmit() throws ProcessException {

		OrderData data = new OrderData();
		data.setEvent(OrderEvent.submit);
		data = (OrderData) stateTrasitionsManager.processEvent(data);

		return ((OrderEvent) data.getEvent()).name() + ", orderId = " + data.getOrderId();
	}
}
