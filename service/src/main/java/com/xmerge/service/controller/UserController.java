package com.xmerge.service.controller;


import com.xmerge.constant.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.service.UserService;
import com.xmerge.web.globalResult.GlobalResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/service/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/list")
    public List<UserDO> list() {
        return userService.list();
    }

    @GetMapping("/get")
    public UserDO get(@RequestParam  String username) {
        return userService.getByUsername(username);
    }

    @PostMapping("/register")
    public boolean add(@RequestBody UserDO userDO) {
        return userService.register(userDO);
    }

    @GetMapping("/test")
    public Result<Void> test() {
        return GlobalResult.success();
    }

    @GetMapping("/testFail")
    public Result<Void> testFail() {
        throw new RuntimeException("test fail");
    }
}
