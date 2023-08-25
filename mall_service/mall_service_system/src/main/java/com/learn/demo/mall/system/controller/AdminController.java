package com.learn.demo.mall.system.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.system.enums.SystemErrorCodeEnum;
import com.learn.demo.mall.system.pojo.AdminPO;
import com.learn.demo.mall.system.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员用户管理
 *
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/system/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/{username}")
    public Result<AdminPO> queryByUsername(@PathVariable String username) {
        return Result.success(adminService.queryByUsername(username));
    }

    @PostMapping
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
    public void login(@RequestBody AdminPO admin, HttpServletResponse response) {
        if (StringUtils.isBlank(admin.getLoginName())) {
            throw new BaseBizException("用户名不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(admin.getPassword())) {
            throw new BaseBizException("密码不能为空", SystemErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        adminService.login(admin, response);
    }

}
