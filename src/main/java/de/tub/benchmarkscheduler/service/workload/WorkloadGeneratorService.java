package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.Workload;

public interface WorkloadGeneratorService {


    Workload generateWorkload(WorkloadStrategy strategy, String bpId);

    Workload genereateExcWorkload(String workloadId, String token, String vdcId);

    String generateDefault(String bpId);
}
