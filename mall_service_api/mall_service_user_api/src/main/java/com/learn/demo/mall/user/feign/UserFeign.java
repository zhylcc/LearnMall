package com.learn.demo.mall.user.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.user.pojo.UserPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zh_cr
 */
@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/info/load/{username}")
    Result<UserPO> loadByUsername(@PathVariable String username);

    @PostMapping("/user/info/points")
    public Result<Void> updatePoints(@RequestParam Integer points);
}
