package restful.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import restful.entity.UserClient;

import java.util.List;

@Repository 
@Mapper 
public interface ClientUserMapper {

    @Select(value = "select * from users")
    List<UserClient> query();
    
}
