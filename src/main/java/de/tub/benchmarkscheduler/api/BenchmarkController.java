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

import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.service.workload.WorkloadDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/benchmark")
@RestController
@Api(value = "Benchmark endpoint", description = "endpoint used by the benchmark runners")
public class BenchmarkController {

    @Autowired
    WorkloadDataService workloadService;

    @ApiOperation(value = "returns an executable workload for the given id", response = Workload.class, produces = "application/json")
    @RequestMapping("/{runID}")
    public Workload getWorkload(@PathVariable String runID) {
        return workloadService.findByID(runID);
    }
}
