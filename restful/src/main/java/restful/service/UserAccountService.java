package restful.service;

import restful.entity.UserEntity;

import java.util.List;

public interface UserAccountService {

    List<UserEntity> allUsers();

    UserEntity queryById(Long userId);
}
