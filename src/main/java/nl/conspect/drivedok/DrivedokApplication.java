package nl.conspect.drivedok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //Ø(exclude = {SecurityAutoConfiguration.class})
public class DrivedokApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivedokApplication.class, args);
    }

}