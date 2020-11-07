package org.aliuselly.sms.service.impl;

import org.aliuselly.sms.dao.AdminMapper;
import org.aliuselly.sms.domain.Admin;
import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控管理员信息
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdminServiceImpl implements AdminService {

//    注入 mapper 接口对象
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(LoginForm loginForm) {
        return adminMapper.login(loginForm);
    }

    @Override
    public Admin findByName(String name) {
        return adminMapper.findByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int insert(Admin admin) {
        return adminMapper.insert(admin);
    }

    @Override
    public List<Admin> selectList(Admin admin) {
        return adminMapper.selectList(admin);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePassword(Admin admin) {
        return adminMapper.updatePassword(admin);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Integer[] ids) {
        return adminMapper.deleteById(ids);
    }
}
