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
import de.tub.benchmarkscheduler.repo.SampleDataRepository;
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
public class SampleDataCTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    SampleDataRepository repo;

    SampleData data;

    String sampleData;

    final ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:4.0-xenial")
            .withFixedExposedPort(27018, 27017);

    private String readToString(String s) {
        return TestHelper.readToString(s);
    }

    @BeforeClass
    public static void checkIfFilesPresent() {
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/SampleData.json"));

    }

    @After
    public  void cleanup(){
        repo.deleteAll();
    }
    @Before
    public void setup()throws Exception{
        sampleData = readToString("/SampleData.json");
         data = mapper.readValue(sampleData, SampleData.class);
    }

    @Test
    public void testCollect() throws Exception{
        mvc.perform(post("/sd/collect").content(sampleData).contentType("application/json")).andExpect(status().isOk());
        Assert.assertEquals(repo.findAll().size(),1);
    }

    @Test
    public void testAll() throws Exception{
        repo.save(data);
        mvc.perform(get("/sd/all")).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<SampleData> sampleDataList = Arrays.asList(mapper.readValue((mvcResult.getResponse().getContentAsString()), SampleData[].class));
                    Assert.assertEquals(sampleDataList.size(), repo.findAll().size());
                });
    }

    @Test
    public void testDelete() throws Exception {
        repo.save(data);
        Assert.assertEquals(repo.findAll().size(),1);
        mvc.perform(delete("/sd/delete")).andExpect(status().isOk());
        Assert.assertEquals(repo.findAll().size(),0);
    }



}
