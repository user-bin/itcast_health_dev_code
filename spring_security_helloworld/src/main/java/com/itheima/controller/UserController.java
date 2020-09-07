package com.itheima.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("add...");
        return "success";
    }

    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('edit')")
    public String edit(){
        System.out.println("edit...");
        return "success";
    }

    @RequestMapping("/select")
    @PreAuthorize("hasAuthority('select')")
    public String select(){
        System.out.println("select...");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('delete')")
    public String delete(){
        System.out.println("delete...");
        return "success";
    }
}