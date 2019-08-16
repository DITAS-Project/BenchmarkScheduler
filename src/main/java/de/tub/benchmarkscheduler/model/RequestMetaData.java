package de.tub.benchmarkscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestMetaData {

    @ApiModelProperty(example = "10")
    private int warmup;

    @ApiModelProperty(example = "20")
    private int iterations;

    @ApiModelProperty(example = "1")
    private int threads;

    @ApiModelProperty(example = "0")
    private int delay;

    @ApiModelProperty(example = "localhost:80")
    private String baseUrl;


    public int getWarmup() {
        return warmup;
    }

    public void setWarmup(int warmup) {
        this.warmup = warmup;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }
}
