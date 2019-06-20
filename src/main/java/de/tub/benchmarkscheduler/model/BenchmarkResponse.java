package de.tub.benchmarkscheduler.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class BenchmarkResponse {

    @ApiModelProperty(example = "{\"mgs\":\"Hello Dal!\"}")
    private String body;

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
    private long duration;

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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
