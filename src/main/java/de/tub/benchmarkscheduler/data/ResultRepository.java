package de.tub.benchmarkscheduler.data;

import de.tub.benchmarkscheduler.model.RawResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultRepository extends MongoRepository<RawResult, String> {


}
