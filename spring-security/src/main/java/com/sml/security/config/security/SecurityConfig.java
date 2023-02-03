package com.sml.security.config.security;

import com.sml.platform.constant.Constant;
import com.sml.security.filter.JwtAuthenticationTokenFilter;
import com.sml.security.handler.RedisStoreTokenLogoutHandler;
import com.sml.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.annotation.Resource;
import java.io.PrintWriter;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    RedisStoreTokenLogoutHandler redisStoreTokenLogoutHandler;

    @Resource
    JwtAuthenticationTokenFilter tokenFilter;

    @Resource
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                .securityContext()
                .securityContextRepository(securityContextRepository())
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login", "/logout").anonymous()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, res, authException) -> {
                    res.setContentType("application/json;charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write("检测到未登录状态，请先登录");
                    out.flush();
                    out.close();
                })
                .accessDeniedHandler((req, res, accessDenied) -> {
                    res.setContentType("application/json;charset=utf-8");
                    PrintWriter out = res.getWriter();
                    out.write("权限不足");
                    out.flush();
                    out.close();
                })
                .and()
                //自动注销
                .logout()
                .deleteCookies("JSESSIONID", "SESSION")
                .addLogoutHandler(redisStoreTokenLogoutHandler)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                //记住我
                .rememberMe()
                .and()
                .cors();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public RedisStoreTokenLogoutHandler storeTokenLogoutHandler(RedisTemplate<String, Object> beanRedisTemplate) {
        return new RedisStoreTokenLogoutHandler(Constant.TOKEN_PREFIX, beanRedisTemplate);
    }
}

