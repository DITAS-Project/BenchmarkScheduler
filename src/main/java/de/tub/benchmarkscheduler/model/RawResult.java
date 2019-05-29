package de.tub.benchmarkscheduler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document
public class RawResult {

    private List<BenchmarkResponse> responses;

    private RequestMetaData metaData;

    private String runnerId;

    private String wlId;

    private String vdcId;

    @Id
    private String id;


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

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getWlId() {
        return wlId;
    }

    public void setWlId(String wlId) {
        this.wlId = wlId;
    }

    public String getVdcId() {
        return vdcId;
    }

    public void setVdcId(String vdcId) {
        this.vdcId = vdcId;
    }

    public String getId(){
        if(this.id==null)this.id=runnerId+vdcId+wlId;
        return this.id;
    }
}
