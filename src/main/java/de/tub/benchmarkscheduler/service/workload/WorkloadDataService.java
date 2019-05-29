package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.model.Workload;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;



public interface WorkloadDataService {

    void save(Workload workload);
    void saveAll(Collection<Workload> workloads);
    List<Workload> getAll();
    void deleteAll();
    Workload findByID(String id);
}
