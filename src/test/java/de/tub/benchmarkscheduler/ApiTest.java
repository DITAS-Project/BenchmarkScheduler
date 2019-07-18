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
import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.model.SampleData;
import de.tub.benchmarkscheduler.model.StartRequest;
import de.tub.benchmarkscheduler.model.Workload;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiTest {

    @Autowired
    MockMvc mvc;

    @ClassRule
    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:4.0-xenial")
            .withFixedExposedPort(27018, 27017);

    static String wlID;
    static String runID;

    ObjectMapper mapper = new ObjectMapper();

    SampleData data;

    private final String vdcId = "ditasVDC";

    @Before
    public void setup() throws Exception {

        String sampleData = readToString("/SampleData.json");

        data = mapper.readValue(sampleData, SampleData.class);
        mvc.perform(post("/sd/collect").content(sampleData).contentType("application/json")).andExpect(status().isOk());


    }

    private String readToString(String s) {
        return TestHelper.readToString(s);
    }

    @BeforeClass
    public static void checkIfFilesPresent() {
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/SampleData.json"));
    }

    /*
    CommandController aka CC Tests
     */

    @Test
    public void testCCGetAll() throws Exception {
        ResultActions perform = mvc.perform(get("/sd/all"));
        perform.andExpect(status().isOk()).andExpect(mvcResult -> {
            List<SampleData> sampleDataList = Arrays.asList(mapper.readValue((mvcResult.getResponse().getContentAsString()), SampleData[].class));
            Assert.assertEquals(sampleDataList.size(), 1);
            Assert.assertEquals(sampleDataList.get(0).getId(), data.getId());
        });

    }

    @Test
    public void testCCCreate() throws Exception {
        ResultActions perform = mvc.perform(post("/command/create?blueprint_id=testBP"));
        perform.andExpect(status().is2xxSuccessful()).andExpect(mvcResult -> {
            String createdWlUrl = mvcResult.getResponse().getHeader("Location");
            Assert.assertNotNull(createdWlUrl);
            String[] split = createdWlUrl.split("/");
            wlID = split[split.length - 1];
            mvc.perform(get("/wl/" + wlID)).andExpect(status().isOk());
        });

    }

    @Test
    public void testCCStart() throws Exception {
        StartRequest body = new StartRequest();
        body.setWlId(wlID);
        body.setVdcId(vdcId);

        ResultActions start = mvc.perform(post("/command/start").contentType("application/json").content(mapper.writeValueAsString(body)));
        start.andExpect(status().is2xxSuccessful()).andExpect(mvcResult -> {
            String createdBenchUrl = mvcResult.getResponse().getHeader("Location");
            Assert.assertNotNull(createdBenchUrl);
            String[] split = createdBenchUrl.split("/");
            runID = split[split.length - 1];
            mvc.perform(get("/benchmark/" + runID)).andExpect(status().isOk());
        });
    }


    @Test
    public void testCCDeleteAll() throws Exception {
        ResultActions perform = mvc.perform(delete("/sd/delete"));
        perform.andExpect(status().isOk());
        ResultActions deleted = mvc.perform(get("/sd/all"));
        deleted.andExpect(status().isOk()).andExpect(mvcResult -> {
            List<SampleData> sampleDataList = Arrays.asList(mapper.readValue((mvcResult.getResponse().getContentAsString()), SampleData[].class));
            Assert.assertEquals(sampleDataList.size(), 0);
        });
    }

    /*
    BenchmarkController aka BC tests
     */


    @Test
    public void testBCGetWorkload() throws Exception {
        ResultActions perform= mvc.perform((get("/benchmark/"+runID)));
        perform.andExpect(status().isOk()).andExpect(mvcResult -> {
            Workload result= mapper.readValue(mvcResult.getResponse().getContentAsString(), Workload.class);
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getId(),runID);
            Assert.assertTrue(result.isExecutable());
        });
    }

    static final String resultId="someID";
    static final String resultWL="someWLID";
    static final String resultVdc= "someVDC";

    @Test
    public void testBCCollectResults() throws Exception{
        RawResult testResult= new RawResult();
        testResult.setId(resultId);
        testResult.setWlId(resultWL);
        testResult.setVdcId(resultVdc);

        ResultActions perform= mvc.perform(post("/benchmark/"+ runID).contentType("application/json").content(mapper.writeValueAsString(testResult)));
        perform.andExpect(status().is2xxSuccessful());

    }

    public void testBCGetAllResults() throws Exception {
     ResultActions perform = mvc.perform(get("/benchmark/results"));
     perform.andExpect(status().isOk()).andExpect(mvcResult -> {
         List<RawResult> results= Arrays.asList(mapper.readValue(mvcResult.getResponse().getContentAsString(),RawResult[].class));
         Assert.assertEquals(results.size(),0);
         RawResult result= results.get(0);
         Assert.assertNotNull(result);
         Assert.assertEquals(result.getId(),resultId);
         Assert.assertEquals(result.getVdcId(), resultVdc);
         Assert.assertEquals(result.getWlId(), resultWL);
     });
    }


}
