package security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication 
public class SecuritySpringApplication implements CommandLineRunner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySpringApplication.class);

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SecuritySpringApplication.class);
        builder.build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        LOGGER.info("程序运行");
    }
    
    
}






