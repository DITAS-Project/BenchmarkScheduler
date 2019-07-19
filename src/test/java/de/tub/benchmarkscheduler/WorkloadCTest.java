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
import de.tub.benchmarkscheduler.model.Workload;
import de.tub.benchmarkscheduler.repo.WorkloadRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkloadCTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    WorkloadRepository repo;



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

    }
    @After
    public  void cleanup(){
        repo.deleteAll();
    }
    @Before
    public void setup() throws Exception{
        String workloadString= readToString("/Workload.json");
        Workload workload= mapper.readValue(workloadString, Workload.class);
        repo.save(workload);
    }

    @Test
    public void testGetWlByID() throws Exception{
        List<Workload> workloads = repo.findAll();
        Assert.assertNotNull(workloads.get(0));

        String testId = workloads.get(0).getId();
        mvc.perform(get("/wl/"+ testId)).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    Workload result= mapper.readValue(mvcResult.getResponse().getContentAsString(),Workload.class);
                    Assert.assertEquals(result.getId(),testId);
                });
    }

    @Test
    public void testGetAllWL() throws Exception{

        mvc.perform(get("/wl/all")).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<Workload> results= Arrays.asList(mapper.readValue(mvcResult.getResponse().getContentAsString(),Workload[].class));
                    Assert.assertEquals(repo.findAll().size(),results.size());
                });
    }

    @Test
    public void testDeleteAll() throws Exception{
        Assert.assertTrue(repo.findAll().size()>0);
        mvc.perform(delete("/wl")).andExpect(status().isOk());
        Assert.assertEquals(repo.findAll().size(),0);
    }
}
