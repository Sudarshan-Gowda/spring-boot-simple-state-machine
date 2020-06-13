package com.star.sud.event;

import com.star.sud.process.exception.ProcessException;

public interface StateTransitionsManager {
    public ProcessData processEvent(ProcessData data) throws ProcessException;
}
