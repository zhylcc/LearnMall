package com.learn.demo.mall.user.service;

import com.learn.demo.mall.user.dao.AddressMapper;
import com.learn.demo.mall.user.pojo.AddressPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zh_cr
 */
@Service
public class AddressService {

    @Resource
    private AddressMapper addressMapper;

    public List<AddressPO> queryByUsername(String userName) {
        AddressPO address = new AddressPO();
        address.setUsername(userName);
        return addressMapper.select(address);
    }
}
