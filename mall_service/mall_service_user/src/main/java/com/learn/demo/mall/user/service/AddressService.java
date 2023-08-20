package com.learn.demo.mall.user.service;

import com.learn.demo.mall.user.pojo.AddressPO;
import com.learn.demo.mall.user.dao.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zh_cr
 */
@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public List<AddressPO> queryByUsername(String userName) {
        AddressPO address = new AddressPO();
        address.setUsername(userName);
        return addressMapper.select(address);
    }
}
