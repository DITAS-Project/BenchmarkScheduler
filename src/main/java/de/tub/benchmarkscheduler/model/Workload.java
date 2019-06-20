package de.tub.benchmarkscheduler.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Document
public class Workload {

    @GeneratedValue
    @Id
    @ApiModelProperty(example = "5cfa626a37df1044d2c0064f")
    private String id;

    private List<List<BenchmarkRequest>> requests;

    @ApiModelProperty(example = "0")
    private int delay;

    @ApiModelProperty(example = "20")
    private int iterations;

    @ApiModelProperty(example = "10")
    private int warmup;

    @ApiModelProperty(example = "sequential")
    private String strategy;

    @ApiModelProperty(example = "some.id.token")
    private String token;

    @ApiModelProperty(example = "1")
    private int threads;

    @ApiModelProperty(example = "true")
    private boolean isExecutable;

    public List<List<BenchmarkRequest>> getRequests() {
        return requests;
    }

    public void setRequests(List<List<BenchmarkRequest>> requests) {
        this.requests = requests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getWarmup() {
        return warmup;
    }

    public void setWarmup(int warmup) {
        this.warmup = warmup;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }
}

