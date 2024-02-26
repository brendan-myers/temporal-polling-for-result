# Temporal Polling for Result Example

Run a workflow that either returns immediately when a result is signalled to it, or polls for a result every n seconds.

The demonstrates the case where the result of a request to an external system is not immediately available. Typically;
1. A request is made to the external service, and a callback handler is registered.
2. The request completes, and the result is passed back to the caller via the callback handler.

In the case where the request is made by a Temporal workflow/activity, but the external system is not aware of Temporal (and hence can't use signals to communicate the result), an external callback handler that can forward the result to Temporal is required. This is a point of failure. To overcome this, we use a signal handler to register the result from the callback handler, and also a timer that triggers a check for the result after a given timeout. This avoids the need to constantly poll for the result.

By default, afer starting the workflow the callback handler is called after 30 seconds. The workflow will also poll for a result every 10 seconds.
* To run the default behaviour (callback handler is used), let the default path run.
* To run the case where a result is available outside of the callback handler, call the `/set` endpoint.

## Requirements

*(AKA What I had installed)*

* Java 1.8+
* Local Temporal Server. This is straightforward with the [Temporal CLI](https://github.com/temporalio/cli)

## Usage

* `./gradlew bootRun` - starts the server.
* `http://localhost:8080/` - start a workflow. Returns after the callback has been triggered.
* `http://localhost:8080/callback` - callback function that signals the workflow with the result.
* `http://localhost:8080/get` - returns a result if available.
* `http://localhost:8080/set` - make the result available. 
* `http://localhost:8080/reest` - make the result unavailable.



## Learn more about Temporal and the Java SDK

* [Temporal Hompage](https://temporal.io/)
* [Temporal Java SDK Samples](https://github.com/temporalio/samples-java)
* [Java SDK Guide](https://docs.temporal.io/dev-guide/java)