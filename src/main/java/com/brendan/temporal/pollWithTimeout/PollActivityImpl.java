package com.brendan.temporal.pollWithTimeout;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

import io.temporal.spring.boot.ActivityImpl;

@Component
@ActivityImpl(taskQueues = "pollWithTimeout-queue")
public class PollActivityImpl implements PollActivity {

    @Override
    public String poll() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/get"))
                .timeout(Duration.ofSeconds(1))
                .build();
        
        HttpResponse<String> response = client.send(
            request, 
            HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }
    
}
