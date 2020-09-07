package com.itheima.security;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
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

        Role role = new Role();
        role.setKeyword("ROLE_ADMIN");
        Permission permission1 = new Permission();
        permission1.setKeyword("select");
        Permission permission2 = new Permission();
        permission2.setKeyword("add");
        Permission permission3 = new Permission();
        permission3.setKeyword("edit");
        Permission permission4 = new Permission();
        permission4.setKeyword("delete");
        role.getPermissions().add(permission1);
        role.getPermissions().add(permission2);
        role.getPermissions().add(permission3);
        role.getPermissions().add(permission4);
        //user1 为管理员角色， 拥有 增删改查的权限
        user1.getRoles().add(role);



        SysUser user2 = new SysUser();
        user2.setUsername("xiaoming");
        user2.setPassword(passwordEncoder.encode("1234"));

        Role role1 = new Role();
        role1.setKeyword("ROLE_USER");
        role1.getPermissions().add(permission1);
        //user2 为用户角色，只有查的权限
        user2.getRoles().add(role1);

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
        // 循环对应的角色
        for (Role role : sysUser.getRoles()) {
            SimpleGrantedAuthority grantedAuthority =new SimpleGrantedAuthority(role.getKeyword());
            authorityList.add(grantedAuthority);
            //循环角色中的权限
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority(permission.getKeyword());
                authorityList.add(grantedAuthority1);
            }
        }

//        //添加相应的权限
//        SimpleGrantedAuthority grantedAuthority =new SimpleGrantedAuthority("ROLE_ADMIN");
//        //把权限对象添加到集合中
//        authorityList.add(grantedAuthority);
        //要求返回UserDetails ， 创建该对象
        UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(),authorityList );
        return userDetails;
    }

}
