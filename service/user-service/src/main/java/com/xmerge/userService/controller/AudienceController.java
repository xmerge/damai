package com.xmerge.userService.controller;

import com.xmerge.idempotent.annotation.Idempotent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 观影人表 前端控制器
 * </p>
 *
 * @author Xmerge
 * @since 2023-12-23
 */
@RestController
@RequestMapping("/userService/audience")
public class AudienceController {
    @Idempotent
    @RequestMapping("/test")
    public String test() {
        try{
            Thread.sleep(2000);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "test";
    }
}
