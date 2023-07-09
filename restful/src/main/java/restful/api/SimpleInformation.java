package restful.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restful.entity.UserEntity;
import restful.mapper.SimpleUserMapper;
import restful.service.UserAccountService;

import javax.annotation.Resource;
import java.util.List;

@RestController 
@RequestMapping(path = {"api/v1/users"}, name = "test")
public class SimpleInformation {

    private static final Logger log = LoggerFactory.getLogger(SimpleInformation.class);

    private final UserAccountService userService;

    public SimpleInformation(UserAccountService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {""})
    public List<UserEntity> queryUsers() {
        List<UserEntity> users = this.userService.allUsers();
        log.info("query all users --> {}", users);

        return users;
    }

    @GetMapping(path = {"{user_id}"})
    public UserEntity queryUserById(@PathVariable(value = "user_id") Long userId) {
        UserEntity user = this.userService.queryById(userId);
        log.info("query user by id --> {}", user);

        return user;
    }

    
}
