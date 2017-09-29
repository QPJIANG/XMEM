package demo.pqjiang.service;

import demo.pqjiang.entity.CustomUserDetails;
import demo.pqjiang.mapper.SecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityService implements UserDetailsService {


    @Autowired
    private SecurityMapper securityMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        System.out.println("user login:"+userName);


        List<Map> users = null;
        try {
            users = securityMapper.getUserByName(userName);
        } catch (Exception E) {
            throw new UsernameNotFoundException("fialed to get data from database");
        }
        if (users == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        System.out.println("table size:" + users.size());
        if (users.size() != 1) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        Map userInfo = users.get(0);
        CustomUserDetails user = new CustomUserDetails();
        user.setUsername(userInfo.get("user_name").toString());
//        user.setPassword("df4e62074218968c9bf93a086c2f5d66");
        user.setPassword(userInfo.get("user_password").toString());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。

//            authorities.add(new SimpleGrantedAuthority("admin"));
//            authorities.add(new SimpleGrantedAuthority("manager"));
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);


    }
}
