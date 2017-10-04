package demo.pqjiang.mapper;

import java.util.List;
import java.util.Map;

public interface SecurityMapper {
    /**
     * list all user
     * */
    public List<Map> getUser();

    /**
     * list user by name
     *
     * parameters: user_name
     * */
    public List<Map> getUserByName(String name);
    /**
     * list user by name
     *
     * parameters: user_name
     * */
    public List<Map> getRolesByName(String name);
    /**
     * get all security uri for security conf
     *
     * */
    public List<Map> getSecurityUri();
}
