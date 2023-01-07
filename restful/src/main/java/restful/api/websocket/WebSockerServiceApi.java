package restful.api.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <a href="https://spring.io/guides/gs/messaging-stomp-websocket/">spring websocket doc </a>
 * 使用长连接的应用 
 * @author wangy
 *
 */

@RestController 
@RequestMapping(path = {""}) 
public class WebSockerServiceApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSockerServiceApi.class);
    
}




