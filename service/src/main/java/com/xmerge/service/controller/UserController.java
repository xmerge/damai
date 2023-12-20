package com.xmerge.service.controller;


import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ClientErrorCode;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.service.UserService;
import com.xmerge.web.globalResult.GlobalResult;
import org.apache.catalina.connector.ClientAbortException;
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
    public Result<String> test() {
        return GlobalResult.success("Hello World!");
    }

    @GetMapping("/testFail")
    public Result<String> testFail() {
        // 该异常会被damai-framework中的web模块中的GlobalExceptionHandler捕获
        throw new ClientException(ServerErrorCode.BASE_ERROR);
    }
}
