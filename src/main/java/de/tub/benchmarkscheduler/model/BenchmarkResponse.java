package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class BenchmarkResponse {

    @ApiModelProperty(example = "{\"mgs\":\"Hello Dal!\"}")
    private String body;

    @JsonUnwrapped
    @ApiModelProperty(example = "{\"X-Ditas-Operationid\": [\n" +
            "                \"\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Requestid\": [\n" +
            "                \"e739b640-7885-51a9-8046-e68578ab6640\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Sample\": [\n" +
            "                \"1\"\n" +
            "            ]}")
    private Map<String, List<String>> headers;

    @ApiModelProperty(example = "153")
    private long latency;

    @ApiModelProperty(example = "0")
    private int thread;

    @ApiModelProperty(example = "2")
    private int iteration;

    @ApiModelProperty(example = "200")
    private int statusCode;

    @ApiModelProperty(example = "false")
    private boolean error;


    @ApiModelProperty(example = "e739b640-7885-51a9-8046-e68578ab6640")
    private String reqId;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
