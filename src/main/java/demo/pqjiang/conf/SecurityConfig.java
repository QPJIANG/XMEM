package demo.pqjiang.conf;

import demo.pqjiang.service.SecurityService;
import demo.pqjiang.util.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new SecurityService();
    }

    /**
     * 密码加密
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }}); //user Details Service验证
    }
    /**
     * 拦截配置
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
                .antMatchers("/users/**").hasRole("USER")   // 需要响应的角色才能访问
//                .anyRequest().authenticated()//其他的路径都必须通过身份验证。
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll()//定制登陆行为，登陆后可任意访问
                .and()
                .logout().permitAll();//定制注销行为
    }


//    /**
//     * 认证信息管理
//     * @param auth
//     * @throws Exception
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("configureGlobal");
//        auth
//                .inMemoryAuthentication()  // 认证信息存储于没内存中
//                  .withUser("test").password("test3").roles("USER");
//    }

}
