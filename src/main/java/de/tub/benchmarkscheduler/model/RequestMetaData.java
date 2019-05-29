package de.tub.benchmarkscheduler.model;

public class RequestMetaData{


    private int warmup;
    private int iterations;
    @Deprecated
    private int variance;

    private int delay;
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

    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
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

}
