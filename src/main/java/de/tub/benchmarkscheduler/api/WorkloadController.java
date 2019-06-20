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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wl")
public class WorkloadController {

    @Autowired
    WorkloadDataService workloadDataService;

    @ApiOperation(value = "returns a Workload for the given ID", response = Workload.class, httpMethod = "GET")
    @RequestMapping("/{wlId}")
    public Workload getWLByID(@PathVariable String wlId) {
        return workloadDataService.findByID(wlId);
    }

    @ApiOperation(value = "returns all available Workloads", response = Workload[].class, httpMethod = "GET")
    @RequestMapping("/all")
    public List<Workload> getAllWl() {
        return workloadDataService.getAll();
    }

    @ApiOperation(value = "deletes all Workloads", httpMethod = "DELETE")
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteWlAll() {
        workloadDataService.deleteAll();
    }
}
