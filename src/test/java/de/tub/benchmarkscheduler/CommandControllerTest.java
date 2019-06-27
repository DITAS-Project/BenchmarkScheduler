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
import de.tub.benchmarkscheduler.service.SampleDataService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandControllerTest {

    @Autowired
    MockMvc mvc;

    @ClassRule
    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:4.0-xenial")
            .withFixedExposedPort(27017,27017);

    String wlID;

    ObjectMapper mapper = new ObjectMapper();

    SampleData data;

    private final String vdcId = "ditasVDC";

    @Before
    public void setup() throws Exception {

        String sampleData = readToString("/SampleData.json");

        data = mapper.readValue(sampleData, SampleData.class);
        mvc.perform(post("/collect").content(sampleData).contentType("application/json")).andExpect(status().isOk());


    }

    private String readToString(String s) {
        return TestHelper.readToString(s);
    }

    @BeforeClass
    public static void checkIfFilesPresent() {
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/SampleData.json"));
    }

    @Test
    public void testGetAll() throws Exception {
        ResultActions perform = mvc.perform(get("/command/all"));
        perform.andExpect(status().isOk()).andExpect(mvcResult -> {
            List<SampleData> sampleDataList = Arrays.asList(mapper.readValue((mvcResult.getResponse().getContentAsString()), SampleData[].class));
            Assert.assertEquals(sampleDataList.size(), 1);
            Assert.assertEquals(sampleDataList.get(0).getId(), data.getId());
        });

    }

    @Test
    public void testCreateAndStart() throws Exception {
        ResultActions perform = mvc.perform(post("/command/create?blueprintID=testBP"));
        perform.andExpect(status().is2xxSuccessful()).andExpect(mvcResult -> {
            String createdWlUrl = mvcResult.getResponse().getHeader("Location");
            Assert.assertNotNull(createdWlUrl);
            String[] split = createdWlUrl.split("/");
            wlID = split[split.length - 1];
            mvc.perform(get("/wl/" + wlID)).andExpect(status().isOk());
        });
        StartRequest body = new StartRequest();
        body.setWlId(wlID);
        body.setVdcId(vdcId);

        ResultActions start = mvc.perform(post("/command/start").contentType("application/json").content(mapper.writeValueAsString(body)));
        start.andExpect(status().is2xxSuccessful()).andExpect(mvcResult -> {
            String createdBenchUrl = mvcResult.getResponse().getHeader("Location");
            Assert.assertNotNull(createdBenchUrl);
            String[] split = createdBenchUrl.split("/");
            String runID = split[split.length - 1];
            mvc.perform(get("/benchmark/" + runID)).andExpect(status().isOk());
        });
    }



    @Test
    public void testDeleteAll() throws Exception {
        ResultActions perform = mvc.perform(delete("/command/delete"));
        perform.andExpect(status().isOk());
        ResultActions deleted = mvc.perform(get("/command/all"));
        deleted.andExpect(status().isOk()).andExpect(mvcResult -> {
            List<SampleData> sampleDataList = Arrays.asList(mapper.readValue((mvcResult.getResponse().getContentAsString()), SampleData[].class));
            Assert.assertEquals(sampleDataList.size(), 0);
        });
    }


}
