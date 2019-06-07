package de.tub.benchmarkscheduler.api;


import com.fasterxml.jackson.databind.JsonNode;
import de.tub.benchmarkscheduler.exceptions.WorkloadAlreadyExecuabaleException;
import de.tub.benchmarkscheduler.exceptions.WorkloadNotFoundException;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.SampleDataService;
import de.tub.benchmarkscheduler.service.workload.WorkloadGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/command")
@Api(value = "Command Endpoint", description = "DITAS Service for automated benchmarking")
public class CommandController {

    private final Logger logger = Logger.getLogger("CommandController" + Thread.currentThread().getName());

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private int port;

    @Autowired
    SampleDataService dataService;

    @Autowired
    WorkloadGeneratorService workloadService;

    @ApiOperation(value = "returns all collected requests", response = SampleData[].class, produces = "application/json")
    @RequestMapping("/all")
    public List<SampleData> getAll() {
        return dataService.findAll();
    }

    @ApiOperation(value = "deletes all collected requests")
    @RequestMapping("/delete")
    public void delete() {
        dataService.deleteAll();
    }

    @ApiOperation(value = "generates a workload based on the given blueprint")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "workload created")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestParam String blueprintID) {
        String wlId = workloadService.generateDefault(blueprintID);
        logger.info(address + " " + port + " " + contextPath);
        return ResponseEntity.created(URI.create("http://" + address + ":" + port + contextPath + "/wl/" + wlId)).build();


    }

    @ApiOperation(value = "generates an executable workload based on the vdc_id and token and starts the Benchmark workers")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "benchmark started"),
            @ApiResponse(code = 400, message = "missing workload_id"),
            @ApiResponse(code = 400, message = "missing vdc_id")
    })
    @RequestMapping("/start")
    public ResponseEntity start(@RequestBody JsonNode body) {
        JsonNode wlId = body.get("workload_id");
        JsonNode token = body.get("token");
        JsonNode vdcId = body.get("vdc_id");

        //existence check on the input parameters
        if (wlId == null) return ResponseEntity.badRequest().body("missing workload_id");
        if (vdcId == null) return ResponseEntity.badRequest().body("missing vdc_id");
        String tokenString = "";
        if (token != null) tokenString = token.asText();

        Workload excWl;
        try {
            excWl= workloadService.genereateExcWorkload(wlId.asText(), tokenString, vdcId.asText());
        }catch (WorkloadNotFoundException e){
            return ResponseEntity.badRequest().body("workload "+wlId.asText()+" not found");
        }catch (WorkloadAlreadyExecuabaleException e){
            return ResponseEntity.status(302).header("Location",("http://" + address + ":" + port + contextPath + "/benchmark/" + e.getRunId())).build();
        }


        //set the url for the created concrete Workload == RunID
        return ResponseEntity.created(URI.create("http://" + address + ":" + port + contextPath + "/benchmark/" + excWl.getId())).build();


    }


}
