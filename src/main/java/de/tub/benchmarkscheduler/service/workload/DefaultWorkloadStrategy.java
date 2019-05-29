package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.data.SampleDataRepository;
import de.tub.benchmarkscheduler.model.*;
import de.tub.benchmarkscheduler.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DefaultWorkloadStrategy implements WorkloadStrategy {


    @Override
    public Workload generate(RequestMetaData metaData, List<SampleData> data) {
        Workload workload = new Workload();

        List<Payload> payloads = new LinkedList<>();

        //generate Payloads from Sampledata with one Request per Payload
        for (SampleData s : data) payloads.add(generatePayload(s, metaData));

        workload.setPayloads(payloads);
        return workload;
    }

    /**
     * creates Payload with one benchmark request from sampledata
     * @param data
     * @return
     */
    private Payload generatePayload(SampleData data, RequestMetaData metaData) {
        Payload ret = new Payload();

        //build BenchmarkRequest
        BenchmarkRequest request = makeRequest(data);
        ret.setRequest(Arrays.asList(request));
        ret.setMetaData(metaData);
    return ret;
    }


    /**
     * Overloaded method to create payload with multiple benchmark requests
     * @param data
     * @return
     */
    private Payload generatePayload(Iterable<SampleData> data, RequestMetaData metaData){
        Payload ret= new Payload();

        List<BenchmarkRequest> requests= new LinkedList<>();

        for(SampleData s:data)requests.add(makeRequest(s));

        ret.setRequest(requests);
        ret.setMetaData(metaData);
        return ret;
    }

    /**
     * Generates benchmark request from sampledata
     * @param data
     * @return
     */
    private BenchmarkRequest makeRequest(SampleData data) {
        BenchmarkRequest request = new BenchmarkRequest();

        //build valid response
        ValidResponse response= new ValidResponse();
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
