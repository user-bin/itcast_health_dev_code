package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.SysUser;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    /****
     * 1. 根据用户名从数据库查询用户信息
     * 2. 把获取到的用户信息 封装成UserDetails对象，返回安全框架
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息(包含角色，角色包括权限)
        SysUser sysUser = userService.findByUsername(username);

        if(sysUser != null){
            Collection<GrantedAuthority> authorityList = new ArrayList<>();
            for (Role role : sysUser.getRoles()) {
                for (Permission permission : role.getPermissions()) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getKeyword());
                    authorityList.add(authority);
                }
            }
            /**
             * 参数1 ：用户名
             * 参数2：密码
             * 参数3：权限集合
             */
            UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), authorityList);

            return userDetails;
        }
        return  null;
    }
}
