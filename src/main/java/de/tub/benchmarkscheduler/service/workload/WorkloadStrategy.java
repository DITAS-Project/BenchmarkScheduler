package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.RequestMetaData;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.Workload;

import java.util.List;

public interface WorkloadStrategy {

    Workload generate(RequestMetaData metaData,  List<SampleData> data);
}
