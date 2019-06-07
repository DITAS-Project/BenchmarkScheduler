package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class DefaultWorkloadStrategy implements WorkloadStrategy {


    @Override
    public Workload generate(RequestMetaData metaData, List<SampleData> data) {
        Workload workload = new Workload();

        List<List<BenchmarkRequest>> requests = new LinkedList<>();

        //generate Payloads from Sampledata with one Request per Payload
        for (SampleData s : data) requests.add(generateRequests(s));

        workload.setRequests(requests);

        //set workload meta repo
        workload.setDelay(10);
        workload.setExecutable(false);
        workload.setIterations(20);
        workload.setWarmup(10);
        workload.setThreads(1);
        return workload;
    }

    /**
     * creates one benchmark request from sampledata
     *
     * @param data
     * @return
     */
    private List<BenchmarkRequest> generateRequests(SampleData data) {

        //build BenchmarkRequest
        BenchmarkRequest request = makeRequest(data);

        return Arrays.asList(request);
    }


    /**
     * Overloaded method to create  multiple benchmark requests
     *
     * @param data
     * @return
     */
    private List<BenchmarkRequest> generateRequests(Iterable<SampleData> data, RequestMetaData metaData) {

        List<BenchmarkRequest> requests = new LinkedList<>();

        for(SampleData s:data)requests.add(makeRequest(s));


        return requests;
    }

    /**
     * Generates benchmark request from sampledata
     *
     * @param data
     * @return
     */
    private BenchmarkRequest makeRequest(SampleData data) {
        BenchmarkRequest request = new BenchmarkRequest();

        //build valid response
        ValidResponse response = new ValidResponse();
        response.setResponseBody(data.getResponseBody());
        response.setResponseCode(data.getResponseCode());
        response.setResponseHeader(data.getResponseHeader());
        response.setResponseLength(data.getResponseLength());
        response.setId(data.getId());

        //build request
        request.setMethod(data.getMethod());
        request.setPath(data.getPath());
        request.setRequestHeader(data.getRequestHeader());
        request.getRequestHeader().put("X-Benchmark", Arrays.asList("VDC_Benchmark"));
        request.setValidResponse(response);

        return request;
    }
}
