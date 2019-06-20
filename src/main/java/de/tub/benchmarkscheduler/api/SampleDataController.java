package de.tub.benchmarkscheduler.api;


import com.fasterxml.jackson.databind.JsonNode;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.service.SampleDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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

    @ApiOperation(value = "Endpoint used to collect real Request data from the RM", httpMethod = "POST")
    @ApiResponse(code = 200, message = "OK")
    @RequestMapping(method = RequestMethod.POST)
    public void receiveSample(@RequestBody SampleData body) {
        dataService.save(body);
    }
}
