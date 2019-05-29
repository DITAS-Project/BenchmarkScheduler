package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class BenchmarkRequest {


    private String id= UUID.randomUUID().toString();

    private Map<String, List<String>> requestHeader;
    private String method;
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
