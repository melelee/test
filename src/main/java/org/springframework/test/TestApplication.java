package org.springframework.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TestApplication.class, args);
        TimeUnit.SECONDS.sleep(30);
        List<TestApplication> testApplications = new ArrayList<>();
        while (true){
            testApplications.add(new TestApplication());
        }
    }

}
