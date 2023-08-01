package com.learn.demo.mall.system.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.system.enums.SystemErrorCodeEnum;
import com.learn.demo.mall.system.pojo.AdminPO;
import com.learn.demo.mall.system.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员用户管理
 *
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/")
    public Result<Integer> saveAdmin(@RequestBody AdminPO admin) {
        if (StringUtils.isBlank(admin.getLoginName())) {
            throw new BaseBizException("用户名不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(admin.getPassword())) {
            throw new BaseBizException("初始密码不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        return Result.success(adminService.saveAdmin(admin));
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody AdminPO admin) {
        if (StringUtils.isBlank(admin.getLoginName())) {
            throw new BaseBizException("用户名不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(admin.getPassword())) {
            throw new BaseBizException("密码不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        return Result.success(adminService.login(admin));
    }
}
