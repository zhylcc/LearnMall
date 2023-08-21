package com.learn.demo.mall.system.service;

import com.learn.demo.mall.common.exception.BaseBizException;
import com.learn.demo.mall.system.dao.AdminMapper;
import com.learn.demo.mall.system.enums.SystemErrorCodeEnum;
import com.learn.demo.mall.system.pojo.AdminPO;
import com.learn.demo.mall.system.utils.JwtUtil;
import com.learn.demo.mall.system.utils.SnowflakeIdUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zh_cr
 */
@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private SnowflakeIdUtil snowflakeIdUtil;

    @Resource
    private JwtUtil jwtUtil;

    public AdminPO queryAdminByLoginName(String name) {
        Example example = new Example(AdminPO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andEqualTo("loginName", name);
        }
        return adminMapper.selectOneByExample(example);
    }

    @Transactional
    public Integer saveAdmin(AdminPO admin) {
        admin.setId(null);
        if (Objects.nonNull(queryAdminByLoginName(admin.getLoginName()))) {
            throw new BaseBizException("用户名已存在", SystemErrorCodeEnum.BIZ_ADMIN_WARNING);
        }
        String salt = BCrypt.gensalt();
        String hashPw = BCrypt.hashpw(admin.getPassword(), salt);
        admin.setLoginName(admin.getLoginName());
        admin.setPassword(hashPw);
        adminMapper.insertSelective(admin);
        return admin.getId();
    }


    public String login(AdminPO admin) {
        AdminPO adminResult = queryAdminByLoginName(admin.getLoginName());
        if (Objects.isNull(adminResult)) {
            throw new BaseBizException("用户不存在", SystemErrorCodeEnum.BIZ_ADMIN_NOT_EXIST);
        }
        if (!BCrypt.checkpw(admin.getPassword(), adminResult.getPassword())) {
            throw new BaseBizException("用户名或密码错误", SystemErrorCodeEnum.BIZ_ADMIN_MISMATCH);
        }
        return jwtUtil.createToken(String.valueOf(snowflakeIdUtil.nextId()), "/system/admin/login");
    }
}
