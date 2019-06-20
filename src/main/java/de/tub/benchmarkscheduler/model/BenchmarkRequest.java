package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BenchmarkRequest {


    @ApiModelProperty(example = "e739b640-7885-51a9-8046-e68578ab6640")
    private String id= UUID.randomUUID().toString();

    @ApiModelProperty(example = "{\"X-Ditas-Operationid\": [\n" +
            "                \"\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Requestid\": [\n" +
            "                \"e739b640-7885-51a9-8046-e68578ab6640\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Sample\": [\n" +
            "                \"1\"\n" +
            "            ]}")
    private Map<String, List<String>> requestHeader;

    @ApiModelProperty(example = "GET")
    private String method;

    @ApiModelProperty(example = "/ask")
    private String path;

    @JsonIgnore
    private ValidResponse validResponse;

    public Map<String, List<String>> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, List<String>> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ValidResponse getValidResponse() {
        return validResponse;
    }

    public void setValidResponse(ValidResponse validResponse) {
        this.validResponse = validResponse;
    }
}
