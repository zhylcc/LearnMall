package com.learn.demo.mall.user.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.user.pojo.UserPO;
import com.learn.demo.mall.user.service.TokenDecodeService;
import com.learn.demo.mall.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/user/info")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private TokenDecodeService tokenDecodeService;

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

    @PostMapping("/points")
    public Result<Void> updatePoints(@RequestParam Integer points) {
        String username = KeyConfigUtil.getTestUsername();
        if (StringUtils.isBlank(username)) {
            username = tokenDecodeService.getUserInfo().get("user_name");
        }
        userService.updatePoints(username, points);
        return Result.success();
    }
}
