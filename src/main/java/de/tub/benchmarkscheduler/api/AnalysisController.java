package de.tub.benchmarkscheduler.api;

import de.tub.benchmarkscheduler.model.BenchmarkResponse;
import de.tub.benchmarkscheduler.model.FlatRawResponse;
import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.service.ResultService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
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
    @RequestMapping(method = RequestMethod.GET)
    public List<RawResult> getByDates(@RequestParam
                               @DateTimeFormat(pattern = "dd-MM-yyyy") Date lower,
                               @RequestParam
                               @DateTimeFormat(pattern = "dd-MM-yyyy")Date upper) {
        return service.getBetweenDates(lower,upper);

    }
    @ApiOperation(value = "returns the results for a given timespan in csv Format", response = RawResult[].class, produces = "text/csv", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "lower", value = "lower date bound for result query in dd-MM-yyyy format"),
            @ApiImplicitParam( name = "upper", value = "upper date bound for result query in dd-MM-yyyy format")
    })
    @RequestMapping(value = "/csv",produces = "text/csv", method = RequestMethod.GET)
    public List<FlatRawResponse> getCSV(@RequestParam
                       @DateTimeFormat(pattern = "dd-MM-yyyy") Date lower,
                       @RequestParam
                       @DateTimeFormat(pattern = "dd-MM-yyyy")Date upper){
        List<FlatRawResponse> ret= new LinkedList<>();

        //for better parsing to csv the raw results get mapped so one result only has one BenchmarkResponse
        for(RawResult r:service.getBetweenDates(lower,upper))ret.addAll(flatten(r));


        return ret;


    }

    public List<FlatRawResponse> flatten(RawResult raw){
        List<FlatRawResponse> ret= new LinkedList<>();
        for(BenchmarkResponse br: raw.getResponses()){
            FlatRawResponse add= new FlatRawResponse(raw.getTotalRuntime(),raw.getWlId(),raw.getVdcId(),raw.getId());
            add.setMetaData(raw.getMetaData());
            add.setBody(br.getBody());
            add.setError(br.isError());
            add.setIteration(br.getIteration());
            add.setLatency(br.getLatency());
            add.setReqId(br.getReqId());
            add.setStatusCode(br.getStatusCode());
            add.setThread(br.getThread());
            add.setHeader(br.getHeaders().toString());
            ret.add(add);
        }return ret;
    }
}
