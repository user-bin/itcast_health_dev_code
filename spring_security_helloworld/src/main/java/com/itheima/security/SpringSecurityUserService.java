package com.itheima.security;

import com.itheima.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class SpringSecurityUserService implements UserDetailsService {

    //模拟一个数据库， 模拟一些用户信息
    private static Map<String,SysUser> db = new HashMap<>();
    //静态代码块
    static{
        //创建加密工具类
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        SysUser user1 = new SysUser();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("1234"));

        SysUser user2 = new SysUser();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));

        // 添加到集合中
        db.put(user1.getUsername(), user1);
        db.put(user2.getUsername(), user2);
    }


    /**
     * loadUserByUsername: 根据用户名 载入用户信息(数据库)
     * @param username  从前端传过来的
     * @return  用户信息(UserDetails)
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息
        SysUser sysUser = db.get(username);
        //创建 权限的集合对象
        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        //添加相应的权限
        SimpleGrantedAuthority grantedAuthority =new SimpleGrantedAuthority("ROLE_ADMIN");
        //把权限对象添加到集合中
        authorityList.add(grantedAuthority);
        //要求返回UserDetails ， 创建该对象
        UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(),authorityList );
        return userDetails;
    }

}
