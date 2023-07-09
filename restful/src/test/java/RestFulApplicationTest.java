import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestFulApplicationTest {
    private static final Logger log = LoggerFactory.getLogger(RestFulApplicationTest.class);

    @Test
    public void init() {
        log.info("testing ...");
    }
}
