package de.tub.benchmarkscheduler.api;

import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.service.ResultService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/results")
public class AnalysisController {

    @Autowired
    ResultService service;


    @ApiOperation(value = "returns the results for a given timespan", response = RawResult[].class, produces = "application/json", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "lower", value = "lower date bound for result query in dd-MM-yyyy format"),
            @ApiImplicitParam( name = "upper", value = "upper date bound for result query in dd-MM-yyyy format")
    })
    @RequestMapping
    public List<RawResult> getByDates(@RequestParam
                               @DateTimeFormat(pattern = "dd-MM-yyyy") Date lower,
                               @RequestParam
                               @DateTimeFormat(pattern = "dd-MM-yyyy")Date upper) {

       return service.getBetweenDates(lower,upper);
    }
}
