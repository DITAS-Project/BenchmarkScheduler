package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.RequestMetaData;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WorkloadGeneratorServiceImpl implements WorkloadGeneratorService {

    @Autowired
    WorkloadDataService dataService;

    @Autowired
    SampleDataService sampleDataService;

    @Override
    public Workload generate(WorkloadStrategy strategy, String bpId) {
        RequestMetaData metaData = getMetaData();
        //TODO bpId instead of null when rm is setup to send bpId
        return strategy.generate(metaData, sampleDataService.findByMethodAndBpId("GET", null));
    }



    @Override
    public String generateDefault(String bpId) {
        RequestMetaData metaData=getMetaData();
        if(sampleDataService==null) Logger.getLogger("WL_GEN").info("WTF no DataService");
        DefaultWorkloadStrategy strategy=new DefaultWorkloadStrategy();
        //TODO bpId instead of null when rm is setup to send bpId
        Workload workload = strategy.generate(metaData, sampleDataService.findByMethodAndBpId("GET", null));
        dataService.save(workload);
        return workload.getId();
    }



    private RequestMetaData getMetaData() {
        RequestMetaData metaData= new RequestMetaData();
        metaData.setBaseUrl("http://localhost:80");
        metaData.setDelay(100);
        metaData.setIterations(20);
        metaData.setVariance(0);
        metaData.setWarmup(20);
        return metaData;
    }
}
