package security.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.evs.models.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "auth")
public class AuthEnter {
    private static final Logger log = LoggerFactory.getLogger(AuthEnter.class);

    @PostMapping(path = "login")
    public ResponseEntity<CommonResponse> login(@RequestParam(value = "user_name") String userName, @RequestParam(value = "password") String passwd) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(userName, passwd));

        return ResponseEntity.ok(CommonResponse.buildOk());
    }
}
