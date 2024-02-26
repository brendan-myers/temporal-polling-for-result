# Temporal Polling for Result Example

Run a workflow that either returns immediately when a result is signalled to it, or polls for a result every n seconds. The workflow polls a local server to check whether a result is available.

## Requirements

*(AKA What I had installed)*

* Java 1.8+
* Local Temporal Server. This is straightforward with the [Temporal CLI](https://github.com/temporalio/cli)

## Usage

* `./gradlew bootRun` - starts the server.
* `http://localhost:8080/` - start a workflow. No result is returned (TODO: don't be lazy).
* `http://localhost:8080/get` - check whether the result is available.
* `http://localhost:8080/set` - make the result available. 
* `http://localhost:8080/reest` - make the result unavailable.

## Learn more about Temporal and the Java SDK

* [Temporal Hompage](https://temporal.io/)
* [Temporal Java SDK Samples](https://github.com/temporalio/samples-java)
* [Java SDK Guide](https://docs.temporal.io/dev-guide/java)