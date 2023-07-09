package restful.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import restful.entity.UserEntity;

import java.security.PrivateKey;
import java.util.List;

@Repository 
@Mapper 
public interface SimpleUserMapper {
    String TABLE_NAME = "users";
    String SELECT_ALL_COLUMNS = "id, account_id, name, nick_name, password, create_time, update_time";
    String INSERT_COLUMNS = "account_id, name, password";

    @Select(value = "select " + TABLE_NAME + " from users")
    List<UserEntity> selectAll(); // should pageable

    @Select(value = "select " + SELECT_ALL_COLUMNS + " from " + TABLE_NAME + " where id = #{user_id} ")
    UserEntity selectById(@Param(value = "user_id") Long id);
}
