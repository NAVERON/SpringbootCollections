package restful.service.impl;

import org.springframework.stereotype.Service;
import restful.entity.UserEntity;
import restful.mapper.SimpleUserMapper;
import restful.service.UserAccountService;

import java.util.List;

@Service
public class UserServiceImpl implements UserAccountService {

    private final SimpleUserMapper userMapper;
    public UserServiceImpl(SimpleUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<UserEntity> allUsers() {
        return this.userMapper.selectAll();
    }

    @Override
    public UserEntity queryById(Long userId) {
        return this.userMapper.selectById(userId);
    }
}
