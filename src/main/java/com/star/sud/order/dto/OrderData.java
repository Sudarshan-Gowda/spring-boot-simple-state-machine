package com.star.sud.order.dto;

import com.star.sud.event.ProcessData;
import com.star.sud.event.ProcessEvent;

public class OrderData implements ProcessData {
	private double payment;
	private ProcessEvent event;
	private String orderId;

	@Override
	public ProcessEvent getEvent() {
		return this.event;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setEvent(ProcessEvent event) {
		this.event = event;
	}

}
