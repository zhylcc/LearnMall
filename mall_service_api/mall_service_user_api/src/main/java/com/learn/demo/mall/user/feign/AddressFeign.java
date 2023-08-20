package com.learn.demo.mall.user.feign;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.user.pojo.AddressPO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zh_cr
 */
@FeignClient(name = "user")
public interface AddressFeign {

    @GetMapping("/user/address")
    Result<List<AddressPO>> queryByUsername();
}
