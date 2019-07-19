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

package de.tub.benchmarkscheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tub.benchmarkscheduler.api.CommandController;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.StartRequest;
import de.tub.benchmarkscheduler.model.ValidResponse;
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.repo.SampleDataRepository;
import de.tub.benchmarkscheduler.repo.WorkloadRepository;
import net.bytebuddy.matcher.CollectionOneToOneMatcher;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommandCTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WorkloadRepository workloadRepository;

    @Autowired
    SampleDataRepository dataRepository;

    final ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:4.0-xenial")
            .withFixedExposedPort(27018, 27017);

    private String readToString(String s) {
        return TestHelper.readToString(s);
    }

    @BeforeClass
    public static void checkIfFilesPresent() {
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/Workload.json"));
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/SampleData.json"));
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/StartRequest.json"));
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/ValidResponse.json"));


    }

    @After
    public void cleanup() {
        dataRepository.deleteAll();
        workloadRepository.deleteAll();
    }

    @Test
    public void testCreate() throws Exception {
        dataRepository.save(mapper.readValue(readToString("/SampleData.json"), SampleData.class));
        Assert.assertEquals(workloadRepository.findAll().size(), 0);
        mvc.perform(post("/command/create?blueprint_id=test")).andExpect(status().is(201));

        List<Workload> workloads = workloadRepository.findAll();
        Assert.assertEquals(workloads.size(),1);
        Workload created = workloads.get(0);
        Assert.assertNotNull(created);
        Assert.assertTrue(!created.isExecutable());

    }

    @Test
    public void testStart() throws Exception{

        Workload input = mapper.readValue(readToString("/Workload.json"), Workload.class);
        ValidResponse validResponse= mapper.readValue(readToString("/ValidResponse.json"), ValidResponse.class);
        input.getRequests().get(0).get(0).setValidResponse(validResponse);
        workloadRepository.save(input);

        String wlID= input.getId();

        String startString= readToString("/StartRequest.json");
        StartRequest startRequest= mapper.readValue(startString,StartRequest.class);
        String vdc_id= startRequest.getVdcId();


        mvc.perform(post("/command/start").content(startString).contentType("application/json")).andExpect(status().is(201));

        Optional<Workload> workload = workloadRepository.findById(wlID+"-"+vdc_id);
        Assert.assertTrue(workload.isPresent());
        Assert.assertTrue(workload.get().isExecutable());


    }
}
