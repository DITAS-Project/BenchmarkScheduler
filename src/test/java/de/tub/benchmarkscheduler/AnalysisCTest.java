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
import de.tub.benchmarkscheduler.repo.ResultRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnalysisCTest {

    @Autowired
    MockMvc mvc;


    @Autowired
    ResultRepository repo;

    final ObjectMapper mapper = new ObjectMapper();

    @ClassRule
    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:4.0-xenial")
            .withFixedExposedPort(27018, 27017);

    private String readToString(String s) {
        return TestHelper.readToString(s);
    }
    @BeforeClass
    public static void checkIfFilesPresent() {
        Assert.assertNotNull(CommandController.class.getResourceAsStream("/Result.json"));


    }

    @Before
    public void setup() throws Exception{
        String resultString= readToString("/Result.json");
        RawResult result= mapper.readValue(resultString, RawResult.class);
        result.setDate(new Date());
        repo.save(result);
    }

    @After
    public void cleanup(){
        repo.deleteAll();
    }

    @Test
    public void testGetResults() throws Exception{
        Calendar calender = Calendar.getInstance();
        int upperDateDay=calender.get(Calendar.DAY_OF_MONTH)+1;
        int lowerDateDay= calender.get(Calendar.DAY_OF_MONTH)-1;
        int month = calender.get(Calendar.MONTH)+1;
        int year = calender.get(Calendar.YEAR);
        String upperDate=String.format("%02d-%02d-%04d", upperDateDay, month, year);
        String lowerDate=String.format("%02d-%02d-%04d", lowerDateDay,month,year);
        mvc.perform(get("/results?lower="+lowerDate+"&upper="+upperDate)).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    RawResult[] results= mapper.readValue(mvcResult.getResponse().getContentAsString(),RawResult[].class);
                    Assert.assertEquals(results.length,repo.findAll().size());
                        }

                );
    }

    @Test
    public void testGetCSV() throws Exception{
        Calendar calender = Calendar.getInstance();
        int upperDateDay=calender.get(Calendar.DAY_OF_MONTH)+1;
        int lowerDateDay= calender.get(Calendar.DAY_OF_MONTH)-1;
        int month = calender.get(Calendar.MONTH)+1;
        int year = calender.get(Calendar.YEAR);
        String upperDate=String.format("%02d-%02d-%04d", upperDateDay, month, year);
        String lowerDate=String.format("%02d-%02d-%04d", lowerDateDay,month,year);
        mvc.perform(get("/results/csv?lower="+lowerDate+"&upper="+upperDate)).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                            Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
                            Assert.assertNotEquals(mvcResult.getResponse().getContentAsString(),"[]");
                        }

                );
    }

}
