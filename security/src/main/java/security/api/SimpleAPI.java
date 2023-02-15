package security.api;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class SimpleAPI {

    private static final Logger log = LoggerFactory.getLogger(SimpleAPI.class);

    @RequiresRoles(value = "admin")
    @GetMapping(path = "")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().build();
    }

    @RequiresPermissions(value = "info:read")
    @GetMapping(path = "info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok().build();
    }


}









