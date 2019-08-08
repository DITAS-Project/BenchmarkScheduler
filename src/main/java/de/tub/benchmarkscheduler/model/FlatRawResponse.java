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

package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

public class FlatRawResponse {


    @JsonUnwrapped
    private RequestMetaData metaData;

    @ApiModelProperty(example = "150396")
    private long totalRuntime;

    @ApiModelProperty(example = "fsafdfkdfdsl")
    @JsonAlias("workload_id")
    private String wlId;

    @ApiModelProperty(example = "afkdfsall")
    @JsonAlias("vdc_id")
    private String vdcId;

    @ApiModelProperty(example = "agent_id")
    @Id
    private String id;

    @ApiModelProperty(example = "153")
    private long latency;

    @ApiModelProperty(example = "0")
    private int thread;

    @ApiModelProperty(example = "2")
    private int iteration;

    @ApiModelProperty(example = "200")
    private int statusCode;

    @ApiModelProperty(example = "false")
    private boolean error;

    private String body;

    private String header;


    @ApiModelProperty(example = "e739b640-7885-51a9-8046-e68578ab6640")
    private String reqId;

    public FlatRawResponse(long totalRuntime, String wlId, String vdcId, String id) {
        this.totalRuntime = totalRuntime;
        this.wlId = wlId;
        this.vdcId = vdcId;
        this.id = id;
    }


    public RequestMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(RequestMetaData metaData) {
        this.metaData = metaData;
    }

    public long getTotalRuntime() {
        return totalRuntime;
    }

    public void setTotalRuntime(long totalRuntime) {
        this.totalRuntime = totalRuntime;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonGetter("workload_id")
    public String getWlId() {
        return wlId;
    }

    public void setWlId(String wlId) {
        this.wlId = wlId;
    }

    @JsonGetter("vdc_id")
    public String getVdcId() {
        return vdcId;
    }

    public void setVdcId(String vdcId) {
        this.vdcId = vdcId;
    }

    public String getId() {
        return id;
    }


  public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
