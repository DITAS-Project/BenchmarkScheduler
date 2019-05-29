package de.tub.benchmarkscheduler.api;


import com.fasterxml.jackson.databind.JsonNode;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/collect")
public class SampleDataController {

    @Autowired
    SampleDataService dataService;

    @RequestMapping(method = RequestMethod.POST)
    public void receiveSample(@RequestBody SampleData body){
        dataService.save(body);
    }
}
