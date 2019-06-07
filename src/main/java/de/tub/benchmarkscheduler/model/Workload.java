package de.tub.benchmarkscheduler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;


@Document
public class Workload {

    @GeneratedValue
    @Id
    private String id;

    private List<List<BenchmarkRequest>> requests;

    private int delay;

    private int iterations;

    private int warmup;

    private String strategy;

    private String token;

    private int threads;

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

