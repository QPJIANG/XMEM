package demo.pqjiang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityMapper {
    @Select("select * from user_info")
    public List<Map> getUser();

    @Select("select * from user_info where user_name=#{name}")
    public List<Map> getUserByName(String name);
}
