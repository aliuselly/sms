package org.aliuselly.sms.service.impl;

import org.aliuselly.sms.dao.TeacherMapper;
import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Teacher;
import org.aliuselly.sms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控教师信息
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TeacherServiceImpl implements TeacherService {

//    注入 mapper 接口对象
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(LoginForm loginForm) {
        return teacherMapper.login(loginForm);
    }

    @Override
    public List<Teacher> selectList(Teacher teacher) {
        return teacherMapper.selectList(teacher);
    }

    @Override
    public Teacher findByTno(Teacher teacher) {
        return teacherMapper.findByTno(teacher);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int insert(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Teacher teacher) {
        return teacherMapper.update(teacher);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Integer[] ids) {
        return teacherMapper.deleteById(ids);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePassword(Teacher teacher) {
        return teacherMapper.updatePassword(teacher);
    }
}
