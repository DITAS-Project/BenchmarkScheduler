package de.tub.benchmarkscheduler.service;


import de.tub.benchmarkscheduler.model.SampleData;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SampleDataService {



     void save(SampleData data);

     SampleData findById(String id);

     List<SampleData> findByMethod(String method);

     List<SampleData> findAll();

     void deleteAll();

     List<SampleData> findByMethodAndBpId(String method,String bpId);

}
