package com.learn.demo.mall.gateway;

import com.learn.demo.mall.gateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@Slf4j
public class TokenTest {

    @Resource
    private JwtUtil jwtUtil;

    @Test
    public void test() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDcyNDY3MTUyODk2Iiwic3ViIjoiL3N5c3RlbS9hZG1pbi9sb2dpbiIsImlzcyI6Im1hbGwiLCJpYXQiOjE2OTI4OTM5NjgsImV4cCI6MTY5Mjg5Mzk5OH0.yLLwl8d2FWCA_Z1sIRuWU3taIS7NhJFdRN19-yos3ds";
        Claims claims = jwtUtil.parseToken(token);
        log.info("鉴权成功，token签发时间: " + claims.getIssuedAt());
    }
}
