package de.tub.benchmarkscheduler.api;


import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.SampleDataService;
import de.tub.benchmarkscheduler.service.workload.WorkloadDataService;
import de.tub.benchmarkscheduler.service.workload.WorkloadGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
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

    @Autowired
    WorkloadDataService workloadDataService;

    @RequestMapping("/method/{method}")
    public List<SampleData> getByMethod(@PathVariable String method) {
        return dataService.findByMethod(method);
    }

    @RequestMapping("/all")
    public List<SampleData> getAll() {
        return dataService.findAll();
    }

    @RequestMapping("/delete")
    public void delete() {
        dataService.deleteAll();
    }

    @RequestMapping("/wl/{wlId}")
    public Workload getWLByID(@PathVariable String wlId) {
        return workloadDataService.findByID(wlId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestParam String blueprintID) {
        String wlId = workloadService.generateDefault(blueprintID);
        logger.info(address + " " + port + " " + contextPath);
        return ResponseEntity.created(URI.create("http://" + address + ":" + port + contextPath + "/wl/" + wlId)).build();


    }

    @RequestMapping("wl/all")
    public List<Workload> getAllWl() {
        return workloadDataService.getAll();
    }

    @RequestMapping("wl/delete")
    public void deleteWlAll() {
        workloadDataService.deleteAll();
    }
}
