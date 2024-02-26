package com.brendan.temporal.pollWithTimeout;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PollActivity {
    String poll() throws Exception;
}
