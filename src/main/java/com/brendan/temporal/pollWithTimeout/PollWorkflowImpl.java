package com.brendan.temporal.pollWithTimeout;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;

import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl(taskQueues = "pollWithTimeout-queue")
public class PollWorkflowImpl implements PollWorkflow {
    
    private String result = "";
    
    private PollActivity activity = Workflow.newActivityStub(
        PollActivity.class,
        ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(2))
            .build()
    );

    @Override
    public void signalResult(String result) {
        this.result = result;
    }

    @Value("${timeout_s}")
    private int timeout;

    @Override
    public String getResult() {
        while (result.equals("")) {
            Workflow.await(
                Duration.ofSeconds(timeout), () -> {
                    return !result.equals("");
                }
            );
            if (result.equals("")) {
                try {
                    result = activity.poll();
                } catch (Exception e) {}
            }
        }

        return result;
    }
}
