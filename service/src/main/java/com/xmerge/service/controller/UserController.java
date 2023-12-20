package com.xmerge.service.controller;


import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;
import com.xmerge.service.dao.entity.UserDO;
import com.xmerge.service.dto.req.UserRegisterReqDTO;
import com.xmerge.service.service.UserLoginService;
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
    UserLoginService userLoginService;

    @GetMapping("/list")
    public List<UserDO> list() {
        return userLoginService.list();
    }

    @GetMapping("/get")
    public UserDO get(@RequestParam  String username) {
        return userLoginService.getByUsername(username);
    }

    @PostMapping("/register")
    public Result<UserDO> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        return userLoginService.register(userRegisterReqDTO);
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
