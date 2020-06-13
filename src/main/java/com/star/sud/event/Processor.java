package com.star.sud.event;

import com.star.sud.process.exception.ProcessException;

public interface Processor {
    public ProcessData process(ProcessData data) throws ProcessException;
}
