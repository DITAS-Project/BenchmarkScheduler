package de.tub.benchmarkscheduler.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class ValidResponse {

    @ApiModelProperty(example = "200")
    private Integer responseCode;

    @ApiModelProperty(example = "20")
    private Integer responseLength;

    @ApiModelProperty(example = "{\"mgs\":\"Hello Dal!\"}")
    private String responseBody;

    @ApiModelProperty(example = " {\n" +
            "            \"Access-Control-Allow-Headers\": [\n" +
            "                \"*\"\n" +
            "            ],\n" +
            "            \"Access-Control-Allow-Origin\": [\n" +
            "                \"*\"\n" +
            "            ],\n" +
            "            \"Content-Length\": [\n" +
            "                \"20\"\n" +
            "            ],\n" +
            "            \"Content-Type\": [\n" +
            "                \"application/json\"\n" +
            "            ],\n" +
            "            \"Date\": [\n" +
            "                \"Wed, 29 May 2019 11:16:15 GMT\"\n" +
            "            ]\n" +
            "        }")
    private Map<String, List<String>> responseHeader;

    @ApiModelProperty(example = "e739b640-7885-51a9-8046-e68578ab6640")
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
