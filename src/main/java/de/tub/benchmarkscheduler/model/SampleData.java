package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class SampleData {

    @Id
    private String id;

    @JsonAlias("request.header")
    private Map<String, List<String>> requestHeader;

    @JsonAlias("request.method")
    private String method;

    @JsonAlias("request.path")
    private String path;

    @JsonAlias("response.code")
    private Integer responseCode;

    @JsonAlias("response.length")
    private Integer responseLength;

    @JsonAlias("response.body")
    private String responseBody;

    @JsonAlias("response.header")
    private Map<String, List<String>> responseHeader;

    @JsonAlias("vdc_id")
    private String vdcID;

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
