package de.tub.benchmarkscheduler.repo;

import de.tub.benchmarkscheduler.model.SampleData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SampleDataRepository extends MongoRepository<SampleData, String> {

    List<SampleData> findAllByMethod(String method);

    List<SampleData> findAllByMethodAndBpId(String method, String bpId);
}
