package de.tub.benchmarkscheduler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Document
public class Workload {

    @GeneratedValue
    @Id
    private String id;
    private List<Payload> payloads;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }
}
