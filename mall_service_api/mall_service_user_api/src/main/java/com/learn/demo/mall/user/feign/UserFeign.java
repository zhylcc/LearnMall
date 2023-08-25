package com.learn.demo.mall.user.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.user.pojo.UserPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author zh_cr
 */
@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping("/user/info/load/{username}")
    Result<UserPO> loadByUsername(@PathVariable String username);

    @PutMapping("/user/info/points/{points}")
    Result<Void> updatePoints(@PathVariable Integer points);
}
