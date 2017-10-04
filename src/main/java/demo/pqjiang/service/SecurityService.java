package demo.pqjiang.service;

import demo.pqjiang.entity.CustomUserDetails;
import demo.pqjiang.mapper.SecurityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);
    @Autowired
    private SecurityMapper securityMapper;

    /**
     *  load user token
     * */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        logger.info("user login:"+userName);
        CustomUserDetails user = new CustomUserDetails();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        try {
            List<Map> users  = securityMapper.getUserByName(userName);
            if (users == null || users.size()!= 1) {
                logger.error("failed to get user info from database!");
                throw new UsernameNotFoundException("user not exists");
            }else{
                Map userInfo = users.get(0);

                user.setUsername(userInfo.get("user_name").toString());
                user.setPassword(userInfo.get("user_password").toString());
            }

        } catch (Exception E) {
            logger.error("failed to get data from database while query user information");
            throw new UsernameNotFoundException("failed to get data from database");
        }
        /**
         * set user roles
         * */
        try{
            List<Map> roles =  securityMapper.getRolesByName(userName);
            if(roles != null || roles.size()>0){
                for(Map map:roles){
                    authorities.add(new SimpleGrantedAuthority(map.get("user_role").toString()));
                }
            }
        }catch (Exception e){
            logger.info("fail to get user roles from database,user:"+userName);
        }
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }

}
