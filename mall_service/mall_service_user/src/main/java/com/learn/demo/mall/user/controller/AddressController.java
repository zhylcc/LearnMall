package com.learn.demo.mall.user.controller;

import com.learn.demo.mall.common.response.Result;
import com.learn.demo.mall.common.utils.KeyConfigUtil;
import com.learn.demo.mall.user.pojo.AddressPO;
import com.learn.demo.mall.user.service.AddressService;
import com.learn.demo.mall.user.utils.TokenDecodeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/user/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private TokenDecodeUtil tokenDecodeUtil;

    @GetMapping
    public Result<List<AddressPO>> queryByUsername() {
        String username = tokenDecodeUtil.parseClaims().getUsername();
        return Result.success(addressService.queryByUsername(username));
    }
}
