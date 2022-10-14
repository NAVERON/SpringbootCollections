package restful.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restful.entity.UserClient;
import restful.mapper.ClientUserMapper;

@RestController 
@RequestMapping(path = {"test"}, name = "test")
public class SimpleInformation {

    @Resource 
    private ClientUserMapper userMapper;
    
    @GetMapping(path = {"users"})
    public List<UserClient> queryUsers(){
        return this.userMapper.query();
    }
    
}
