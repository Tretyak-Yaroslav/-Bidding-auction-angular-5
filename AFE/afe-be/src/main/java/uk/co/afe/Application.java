package uk.co.afe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Sergey Teryoshin
 * 02.03.2018 15:30
 */
@SpringBootApplication
@PropertySource(value = "file:./server.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
