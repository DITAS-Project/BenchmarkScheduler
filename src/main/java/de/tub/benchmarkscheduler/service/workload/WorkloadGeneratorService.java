package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.Workload;

public interface WorkloadGeneratorService {


    Workload generate(WorkloadStrategy strategy, String bpId);

    String generateDefault(String bpId);
}
