package com.learn.demo.mall.user.controller;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.user.enums.UserErrorCodeEnum;
import com.learn.demo.mall.user.pojo.UserPO;
import com.learn.demo.mall.user.request.UserLoginReq;
import com.learn.demo.mall.user.utils.TokenDecodeUtil;
import com.learn.demo.mall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/user/info")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private TokenDecodeUtil tokenDecodeUtil;

    @PostMapping("/login")
    public void login(@RequestBody UserLoginReq req, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(req.getClientId()) || StringUtils.isBlank(req.getClientSecret())) {
            throw new BaseBizException("HttpBasic认证失败：客户端ID或密码为空！", UserErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        if (StringUtils.isBlank(req.getUsername()) || StringUtils.isBlank(req.getPassword())) {
            throw new BaseBizException("用户认证失败：用户名或密码为空！", UserErrorCodeEnum.ARGUMENT_ILLEGAL);
        }
        userService.login(req, response);
        log.info("Login success, " + req.getUsername());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.logout(request, response);
        log.info("Logout success.");
    }

    @GetMapping("/{username}")
    public Result<UserPO> queryByUsername(@PathVariable String username) {
        return Result.success(userService.queryByUsername(username));
    }

    @PreAuthorize("hasAnyAuthority('admin', 'ROLE_admin')")
    @DeleteMapping("/{username}")
    public Result<Void> deleteByUsername(@PathVariable String username) {
        userService.deleteByUsername(username);
        return Result.success();
    }

    @GetMapping("/load/{username}")
    public Result<UserPO> loadByUsername(@PathVariable String username) {
        return Result.success(userService.queryByUsername(username));
    }

    @PutMapping("/points/{points}")
    public Result<Void> updatePoints(@PathVariable Integer points) {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        userService.updatePoints(username, points);
        return Result.success();
    }
}
