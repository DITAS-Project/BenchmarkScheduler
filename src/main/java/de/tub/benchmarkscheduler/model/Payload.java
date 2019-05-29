package de.tub.benchmarkscheduler.model;

import java.util.List;

public class Payload {

    private RequestMetaData metaData;
    private List<BenchmarkRequest> request;


    public RequestMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(RequestMetaData metaData) {
        this.metaData = metaData;
    }

    public List<BenchmarkRequest> getRequest() {
        return request;
    }

    public void setRequest(List<BenchmarkRequest> request) {
        this.request = request;
    }
}
