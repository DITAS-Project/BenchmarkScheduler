package de.tub.benchmarkscheduler.service.workload;

import com.sun.xml.messaging.saaj.packaging.mime.util.BEncoderStream;
import de.tub.benchmarkscheduler.exceptions.WorkloadAlreadyExecuabaleException;
import de.tub.benchmarkscheduler.exceptions.WorkloadNotFoundException;
import de.tub.benchmarkscheduler.model.*;
import de.tub.benchmarkscheduler.service.ApiHelper;
import de.tub.benchmarkscheduler.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class WorkloadGeneratorServiceImpl implements WorkloadGeneratorService {

    @Autowired
    WorkloadDataService dataService;

    @Autowired
    SampleDataService sampleDataService;

    @Autowired
    ApiHelper apiHelper;

    @Override
    public Workload generateWorkload(WorkloadStrategy strategy, String bpId) {
        RequestMetaData metaData = getMetaData();
        //TODO bpId instead of null when rm is setup to send bpId
        return strategy.generate(metaData, sampleDataService.findByMethodAndBpId("GET", null));
    }

    @Override
    public Workload genereateExcWorkload(String workloadId, String token, String vdcId) throws WorkloadAlreadyExecuabaleException, WorkloadNotFoundException {
        String runId=workloadId + "-" + vdcId;
        if(dataService.findByID(runId)!=null)throw new WorkloadAlreadyExecuabaleException(runId);
        Workload workload = dataService.findByID(workloadId);
        if(workload==null)throw new WorkloadNotFoundException();
        Workload execWorkload = makeExecutable(workload);

        execWorkload.setToken(token);

        //lookup vdc id to get url
        String baseUrl= apiHelper.getVDCUrl(vdcId);

        //modify the requests
        List<List<BenchmarkRequest>>requests= new LinkedList<>();
        for(List<BenchmarkRequest> l: workload.getRequests())requests.add(modifyRequests(l,baseUrl ));
        execWorkload.setRequests(requests);


        //compose id from workload id and vdc id
        execWorkload.setId(runId);
        dataService.save(execWorkload);
        return execWorkload;
    }


    @Override
    public String generateDefault(String bpId) {
        RequestMetaData metaData = getMetaData();
        if (sampleDataService == null) Logger.getLogger("WL_GEN").info("WTF no DataService");
        DefaultWorkloadStrategy strategy = new DefaultWorkloadStrategy();
        //TODO bpId instead of null when rm is setup to send bpId
        Workload workload = strategy.generate(metaData, sampleDataService.findByMethodAndBpId("GET", null));
        dataService.save(workload);
        return workload.getId();
    }


    private RequestMetaData getMetaData() {
        RequestMetaData metaData = new RequestMetaData();
        metaData.setBaseUrl("http://localhost:80");
        metaData.setDelay(100);
        metaData.setIterations(20);
        metaData.setWarmup(20);
        metaData.setThreads(1);
        return metaData;
    }

    private Workload makeExecutable(Workload w) {
        Workload ret = new Workload();
        ret.setThreads(w.getThreads());
        ret.setWarmup(w.getWarmup());
        ret.setIterations(w.getIterations());
        ret.setDelay(w.getDelay());
        ret.setStrategy("sequential");
        ret.setExecutable(true);

        return ret;

    }

    private List<BenchmarkRequest> modifyRequests(List<BenchmarkRequest> req, String baseUrl) {
        List<BenchmarkRequest> requests = new LinkedList<>();

        for (BenchmarkRequest r : req) {

            BenchmarkRequest request = new BenchmarkRequest();
            request.setMethod(r.getMethod());
            request.setPath(baseUrl + r.getPath());

            //add Benchmark Header and original Header
            Map<String, List<String>> cHeader = new HashMap<>(r.getRequestHeader());
            cHeader.put("X-Benchmark", Arrays.asList("1"));
            cHeader.put("Benchmark-ID", Arrays.asList(r.getValidResponse().getId()));
            request.setRequestHeader(cHeader);

            //add to return list
            requests.add(request);

        }
        return requests;

    }
}
