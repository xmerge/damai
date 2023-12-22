package com.xmerge.userService.controller;


import com.xmerge.biz.dto.UserInfoDTO;
import com.xmerge.convention.exception.ClientException;
import com.xmerge.convention.exception.errorcode.ServerErrorCode;
import com.xmerge.convention.result.Result;
import com.xmerge.userService.dao.entity.UserDO;
import com.xmerge.userService.dto.req.UserLoginReqDTO;
import com.xmerge.userService.dto.req.UserRegisterReqDTO;
import com.xmerge.userService.dto.resp.UserLoginRespDTO;
import com.xmerge.userService.dto.resp.UserRegisterRespDTO;
import com.xmerge.userService.service.UserLoginService;
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
    public Result<UserRegisterRespDTO> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO) {
        return GlobalResult.success(userLoginService.register(userRegisterReqDTO));
    }

    @PostMapping("/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return GlobalResult.success(userLoginService.login(userLoginReqDTO));
    }

    @GetMapping("/check-login")
    public Result<UserInfoDTO> checkLogin() {
        return GlobalResult.success(userLoginService.checkLogin());
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
