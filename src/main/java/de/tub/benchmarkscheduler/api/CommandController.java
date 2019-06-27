package de.tub.benchmarkscheduler.api;


import de.tub.benchmarkscheduler.exceptions.WorkloadAlreadyExecuabaleException;
import de.tub.benchmarkscheduler.exceptions.WorkloadNotFoundException;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.StartRequest;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.SampleDataService;
import de.tub.benchmarkscheduler.service.workload.WorkloadGeneratorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/command")
@Api(value = "Command Endpoint", description = "DITAS Service for automated benchmarking")
public class CommandController {

    private final Logger logger = Logger.getLogger("CommandController" + Thread.currentThread().getName());

    @Bean
    public String hostname() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private int port;

    @Autowired
    SampleDataService dataService;

    @Autowired
    WorkloadGeneratorService workloadService;

    @ApiOperation(value = "returns all collected requests", response = SampleData[].class, produces = "application/json", httpMethod = "GET")
    @RequestMapping("/all")
    public List<SampleData> getAll() {
        return dataService.findAll();
    }

    @ApiOperation(value = "deletes all collected requests", httpMethod = "DELETE")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete() {
        dataService.deleteAll();
    }

    @ApiOperation(value = "generates a workload based on the given blueprint", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "workload created")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestParam String blueprintID    ) {
        String wlId = workloadService.generateDefault(blueprintID);
        String baseUrl= null;
        try {
            baseUrl = hostname();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info(baseUrl + " " + port + " " + contextPath);
        return ResponseEntity.created(URI.create("http://" + baseUrl + ":" + port + contextPath + "/wl/" + wlId)).build();


    }

    @ApiOperation(value = "generates an executable workload based on the vdc_id and token and starts the Benchmark workers", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "benchmark started"),
            @ApiResponse(code = 400, message = "missing workload_id"),
            @ApiResponse(code = 400, message = "missing vdc_id"),
            @ApiResponse(code = 302, message = "workload already created")
    })
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity start(@RequestBody StartRequest body) {

        String baseUrl=null;
        try {
            baseUrl = hostname();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //existence check on the input parameters
        String wlId = body.getWlId();
        String vdcId = body.getVdcId();
        if (wlId == null || wlId == "") return ResponseEntity.badRequest().body("missing workload_id");
        if (vdcId == null || vdcId == "") return ResponseEntity.badRequest().body("missing vdc_id");

        Workload excWl;
        try {
            excWl = workloadService.genereateExcWorkload(wlId, body.getToken(), vdcId);
        } catch (WorkloadNotFoundException e) {
            return ResponseEntity.badRequest().body("workload " + wlId + " not found");
        } catch (WorkloadAlreadyExecuabaleException e) {
            return ResponseEntity.status(302).header("Location", ("http://" + baseUrl + ":" + port + contextPath + "/benchmark/" + e.getRunId())).build();
        }


        //set the url for the created concrete Workload == RunID
        return ResponseEntity.created(URI.create("http://" + baseUrl + ":" + port + contextPath + "/benchmark/" + excWl.getId())).build();


    }


}
