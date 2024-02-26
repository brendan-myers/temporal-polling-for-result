package com.brendan.temporal.pollWithTimeout;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PollWorkflow  {
    @SignalMethod
    void signalResult(String result);

    @WorkflowMethod
    String getResult();
}
