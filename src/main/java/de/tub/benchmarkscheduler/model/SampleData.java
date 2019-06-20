package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class SampleData {

    @ApiModelProperty(example = "e739b640-7885-51a9-8046-e68578ab6640")
    @Id
    private String id;


    @ApiModelProperty(example = "{\"X-Ditas-Operationid\": [\n" +
            "                \"\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Requestid\": [\n" +
            "                \"e739b640-7885-51a9-8046-e68578ab6640\"\n" +
            "            ],\n" +
            "            \"X-Ditas-Sample\": [\n" +
            "                \"1\"\n" +
            "            ]}")
    @JsonAlias("request.header")
    private Map<String, List<String>> requestHeader;

    @ApiModelProperty(example = "GET")
    @JsonAlias("request.method")
    private String method;

    @ApiModelProperty(example = "/ask")
    @JsonAlias("request.path")
    private String path;

    @ApiModelProperty(example = "200")
    @JsonAlias("response.code")
    private Integer responseCode;

    @ApiModelProperty(example = "20")
    @JsonAlias("response.length")
    private Integer responseLength;

    @ApiModelProperty(example = "{\"mgs\":\"Hello Dal!\"}")
    @JsonAlias("response.body")
    private String responseBody;

    @ApiModelProperty(example = "{\n" +
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
    @JsonAlias("response.header")
    private Map<String, List<String>> responseHeader;

    @ApiModelProperty(example = "vdc0")
    @JsonAlias("vdc_id")
    private String vdcID;

    @ApiModelProperty(example = "blueprint1")
    @JsonAlias("blueprint_id")
    private String bpId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseLength() {
        return responseLength;
    }

    public void setResponseLength(int responseLength) {
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

    public String getVdcID() {
        return vdcID;
    }

    public void setVdcID(String vdcID) {
        this.vdcID = vdcID;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }
}
