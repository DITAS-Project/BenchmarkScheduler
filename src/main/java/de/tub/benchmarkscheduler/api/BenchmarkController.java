/*
 *
 *  * Copyright 2018 Information Systems Engineering, TU Berlin, Germany
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *                       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *  * This is being developed for the DITAS Project: https://www.ditas-project.eu/
 *
 */

package de.tub.benchmarkscheduler.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tub.benchmarkscheduler.model.BenchmarkResponse;
import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.ResultService;
import de.tub.benchmarkscheduler.service.workload.WorkloadDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/benchmark")
@RestController
@Api(value = "Benchmark endpoint", description = "endpoint used by the benchmark runners")
public class BenchmarkController {

    @Autowired
    WorkloadDataService workloadService;

    @Autowired
    ResultService resultService;

    @ApiOperation(value = "returns an executable workload for the given id", response = Workload.class, produces = "application/json", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "No workload for the given id found")
    })
    @RequestMapping("/{runID}")
    public Workload getWorkload(@PathVariable String runID) {
        return workloadService.findByID(runID);
    }


    @ApiOperation(value = "Endpoint for the Benchmark Agents to post the results ", httpMethod = "POST")
    @RequestMapping(value = "/{runID}", method = RequestMethod.POST)
    public void collectResults(@RequestBody JsonNode result, @PathVariable String runID) throws Exception{
        Logger.getLogger("benchmark-controller").info(result.toString());
        ObjectMapper mapper= new ObjectMapper();
        RawResult resultObject= mapper.readValue(result.toString(),RawResult.class);
        resultService.save(resultObject);
        Logger.getLogger("benchmark-controller").info(resultObject.getMetaData().getWarmup()+"");
    }

    @ApiOperation(value = "Endpoint to get all Benchmark Results", response = RawResult[].class, produces = "application/json", httpMethod = "GET")
    @RequestMapping("/results")
    public List<RawResult> getAllResults() {
        return resultService.getAll();
    }
}
