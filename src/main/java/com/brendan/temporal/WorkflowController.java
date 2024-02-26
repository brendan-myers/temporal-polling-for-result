package com.brendan.temporal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brendan.temporal.pollWithTimeout.PollWorkflow;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

@RestController
public class WorkflowController {

    @Autowired
    private WorkflowClient client;

    private String result = "";

    @GetMapping("/")
    String index() {
        PollWorkflow workflow = client.newWorkflowStub(
            PollWorkflow.class, 
            WorkflowOptions.newBuilder()
                .setTaskQueue("pollWithTimeout-queue")
                .setWorkflowId("poller-workflow")
                .build()
            );

        return workflow.getResult();
    }

    @GetMapping("/set")
    String set() {
        result = "result-value";
        return "set";
    }

    @GetMapping("/reset")
    String reset() {
        result = "";
        return "reset";
    }

    @GetMapping("/get")
    String get() {
        return result;
    }
}
