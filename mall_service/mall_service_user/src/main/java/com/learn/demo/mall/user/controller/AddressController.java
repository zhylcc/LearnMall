package com.learn.demo.mall.user.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.util.KeyConfigUtil;
import com.learn.demo.mall.user.pojo.AddressPO;
import com.learn.demo.mall.user.service.AddressService;
import com.learn.demo.mall.user.service.TokenDecodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private TokenDecodeService tokenDecodeService;

    @GetMapping
    public Result<List<AddressPO>> queryByUsername() {
        String username = KeyConfigUtil.getTestUsername();
        if (StringUtils.isBlank(username)) {
            username = tokenDecodeService.getUserInfo().get("user_name");
        }
        return Result.success(addressService.queryByUsername(username));
    }
}
