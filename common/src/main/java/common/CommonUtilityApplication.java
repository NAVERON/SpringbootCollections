package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication 
public class CommonUtilityApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CommonUtilityApplication.class);

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CommonUtilityApplication.class);
        builder.build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("running ......");

    }

}



