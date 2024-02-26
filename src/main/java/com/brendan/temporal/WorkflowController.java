package com.brendan.temporal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${timeout_s}")
    private int timeout;

    @GetMapping("/")
    String index() throws Exception {
        PollWorkflow workflow = client.newWorkflowStub(
            PollWorkflow.class, 
            WorkflowOptions.newBuilder()
                .setTaskQueue("pollWithTimeout-queue")
                .setWorkflowId("poller-workflow")
                .build()
            );

        // workflow.getResult();

        WorkflowClient.start(workflow::getResult);

        Thread.sleep(Duration.ofSeconds(timeout * 3));

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/callback"))
                .timeout(Duration.ofSeconds(1))
                .build();
        
        httpClient.send(
            request, 
            HttpResponse.BodyHandlers.ofString()
        );

        // clear the result
        result = "";

        return "finished";
    }

    @GetMapping("/get")
    String get() {
        return result;
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
    @GetMapping("/callback")
    String callback() {
        PollWorkflow workflow = client.newWorkflowStub(
            PollWorkflow.class, 
            "poller-workflow"
        );
        
        workflow.signalResult("result-value-from-callback");

        return "callback";
    }
}
