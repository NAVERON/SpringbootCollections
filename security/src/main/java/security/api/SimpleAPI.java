package security.api;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.service.SimpleService;

@RestController
@RequestMapping(path = "simple")
public class SimpleAPI {

    private static final Logger log = LoggerFactory.getLogger(SimpleAPI.class);
    private final SimpleService simpleService;

    public SimpleAPI(SimpleService simpleService) {
        log.info("simple controller ...");

        this.simpleService = simpleService;
    }

    @RequiresRoles(value = "admin")
    @GetMapping(path = "home")
    public ResponseEntity<String> home() {
        String result = this.simpleService.test1("hello");
        return ResponseEntity.ok(result);
    }

    @RequiresPermissions(value = "info:read")
    @GetMapping(path = "info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok().build();
    }


}









