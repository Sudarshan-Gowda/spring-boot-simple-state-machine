package com.star.sud.order.service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.star.sud.event.ProcessData;
import com.star.sud.event.abs.AbstractStateTransitionsManager;
import com.star.sud.order.OrderEvent;
import com.star.sud.order.OrderState;
import com.star.sud.order.dto.OrderData;
import com.star.sud.order.exception.OrderException;
import com.star.sud.process.exception.ProcessException;

/**
 * This class manages various state transitions based on the event The
 * superclass AbstractStateTransitionsManager calls the two methods
 * initializeState and processStateTransition in that order
 */
@Service
public class OrderStateTransitionsManager extends AbstractStateTransitionsManager {

	private static final Logger log = LoggerFactory.getLogger(OrderStateTransitionsManager.class);

	@Autowired
	private ApplicationContext context;

	@Autowired
	private OrderDbService dbService;

	@Override
	protected ProcessData processStateTransition(ProcessData sdata) throws ProcessException {

		OrderData data = (OrderData) sdata;

		try {
			log.info("Pre-event: " + data.getEvent().toString());
			data = (OrderData) this.context.getBean(data.getEvent().nextStepProcessor(data.getEvent())).process(data);
			log.info("Post-event: " + data.getEvent().toString());
			dbService.getStates().put(data.getOrderId(), (OrderState) data.getEvent().nextState(data.getEvent()));
			log.info("Final state: " + dbService.getStates().get(data.getOrderId()).name());
			log.info("*************************************");

		} catch (OrderException e) {
			log.info("Post-event: " + ((OrderEvent) data.getEvent()).name());
			dbService.getStates().put(data.getOrderId(), (OrderState) data.getEvent().nextState(data.getEvent()));
			log.info("Final state: " + dbService.getStates().get(data.getOrderId()).name());
			log.info("*************************************");
			throw new OrderException(((OrderEvent) data.getEvent()).name(), e);
		}
		return data;
	}

	private OrderData checkStateForReturningCustomers(OrderData data) throws OrderException {
		// returning customers must have a state
		if (data.getOrderId() != null) {
			if (this.dbService.getStates().get(data.getOrderId()) == null) {
				throw new OrderException("No state exists for orderId=" + data.getOrderId());
			} else if (this.dbService.getStates().get(data.getOrderId()) == OrderState.Completed) {
				throw new OrderException("Order is completed for orderId=" + data.getOrderId());
			} else {
				log.info("Initial state: " + dbService.getStates().get(data.getOrderId()).name());
			}
		}
		return data;
	}

	@Override
	protected ProcessData initializeState(ProcessData sdata) throws OrderException {

		OrderData data = (OrderData) sdata;

		if (data.getOrderId() != null) {
			return checkStateForReturningCustomers(data);
		}

		String orderId = UUID.randomUUID().toString();
		data.setOrderId(orderId);
		dbService.getStates().put(orderId, (OrderState) OrderState.Default);

		log.info("Initial state: " + dbService.getStates().get(data.getOrderId()).name());
		return data;
	}

	public ConcurrentHashMap<String, OrderState> getStates() {
		return dbService.getStates();
	}
}
