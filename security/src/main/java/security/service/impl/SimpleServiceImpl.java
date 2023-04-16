package security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import security.service.SimpleService;

@Service
public class SimpleServiceImpl implements SimpleService {

    private static final Logger log = LoggerFactory.getLogger(SimpleServiceImpl.class);

    public SimpleServiceImpl() {
        log.info("simple service ...");
    }

    /**
     * @param input input
     * @return result
     */
    @Override
    public String test1(String input) {
        return input + "---";
    }

}
