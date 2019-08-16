package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawResult {

    private List<BenchmarkResponse> responses;

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

    @JsonIgnore
    private Date date;


    public List<BenchmarkResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<BenchmarkResponse> responses) {
        this.responses = responses;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
