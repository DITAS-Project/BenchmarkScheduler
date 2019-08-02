package de.tub.benchmarkscheduler.repo;

import de.tub.benchmarkscheduler.model.RawResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ResultRepository extends MongoRepository<RawResult, String> {

     List<RawResult> findAllByDateBetween(Date start, Date end);
}
