package de.tub.benchmarkscheduler.model;

import java.util.List;

public class Payload {


    private List<BenchmarkRequest> request;


    public List<BenchmarkRequest> getRequest() {
        return request;
    }

    public void setRequest(List<BenchmarkRequest> request) {
        this.request = request;
    }
}
