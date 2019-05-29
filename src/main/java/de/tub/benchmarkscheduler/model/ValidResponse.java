package de.tub.benchmarkscheduler.model;

import java.util.List;
import java.util.Map;

public class ValidResponse {

    private Integer responseCode;
    private Integer responseLength;
    private String responseBody;
    private Map<String, List<String>> responseHeader;
    private String id;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(Integer responseLength) {
        this.responseLength = responseLength;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Map<String, List<String>> getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(Map<String, List<String>> responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
