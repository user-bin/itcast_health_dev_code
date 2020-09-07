package com.itheima;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestBcrypt {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //长度为60位:
        // $2a$10$H7V9Nj.oJgb4fwhxXuFJpuSia7gi0xPqQ0AgrqiBUpn/o2xn8XHTW
        // $2a$10$6rvzgtIYzFo1pljmk9476.eHvdj/SgfyFNkVj3m/2jCqFnnjY67NK
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);

        boolean b = passwordEncoder.matches("123", "$2a$10$H7V9N.oJgb4fwhxXuFJpuSia7gi0xPqQ0AgrqiBUpn/o2xn8XHTW");
        System.out.println(b);
    }
}
