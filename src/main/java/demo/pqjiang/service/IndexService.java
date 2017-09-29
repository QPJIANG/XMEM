package demo.pqjiang.service;

import demo.pqjiang.mapper.SecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@EnableAutoConfiguration
public class IndexService {
    @Autowired
    public SecurityMapper securityMapper;

    public List<Map> UserList(){
        return securityMapper.getUser();
    }
    public List<Map> getUserByName(String userName){
        return securityMapper.getUserByName(userName);
    }
}
