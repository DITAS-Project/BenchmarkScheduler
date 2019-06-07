package de.tub.benchmarkscheduler.repo;

import de.tub.benchmarkscheduler.model.Workload;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkloadRepository extends MongoRepository<Workload,String> {
}
