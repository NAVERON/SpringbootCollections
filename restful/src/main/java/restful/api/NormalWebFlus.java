package restful.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import restful.entity.UserEntity;
import restful.service.UserAccountService;

@RestController
@RequestMapping(path = {"api/v1/flux"})
public class NormalWebFlus {

    private static final Logger log = LoggerFactory.getLogger(NormalWebFlus.class);

    private final UserAccountService userService;
    public NormalWebFlus(UserAccountService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"users/{user_id}"})
    public Mono<UserEntity> fluxUserById(@PathVariable(value = "user_id") Long userId) {
        UserEntity user = this.userService.queryById(userId);

        return Mono.just(user);
    }
}
