package restful.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import restful.entity.UserClient;

@Repository 
@Mapper 
public interface ClientUserMapper {

    @Select(value = "select * from users") 
    public List<UserClient> query();
    
}
