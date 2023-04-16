package restful.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restful.entity.UserClient;
import restful.mapper.ClientUserMapper;

import javax.annotation.Resource;
import java.util.List;

@RestController 
@RequestMapping(path = {"test"}, name = "test")
public class SimpleInformation {

    private static final Logger log = LoggerFactory.getLogger(SimpleInformation.class);

    @Resource
    private ClientUserMapper userMapper;
    
    @GetMapping(path = {"users"})
    public List<UserClient> queryUsers() {
        return this.userMapper.query();
    }
    
}
