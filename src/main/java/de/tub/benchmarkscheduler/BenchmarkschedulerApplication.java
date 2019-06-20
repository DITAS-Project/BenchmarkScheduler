package de.tub.benchmarkscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"de.tub.benchmarkscheduler"})
@EnableSwagger2
public class BenchmarkschedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkschedulerApplication.class, args);
    }

}
