package quartz;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication 
public class SpringQuartzApplication implements CommandLineRunner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringQuartzApplication.class);

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringQuartzApplication.class);
        builder.build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        
    }
    
    @PreDestroy 
    public void onExit() {
        LOGGER.warn("Closing");
    }
}





