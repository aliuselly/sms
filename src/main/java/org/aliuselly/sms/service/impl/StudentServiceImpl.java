package org.aliuselly.sms.service.impl;

import org.aliuselly.sms.dao.StudentMapper;
import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Student;
import org.aliuselly.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控学生信息
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentServiceImpl implements StudentService {

//    注入 mapper 接口对象
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(LoginForm loginForm) {
        return studentMapper.login(loginForm);
    }

    @Override
    public List<Student> selectList(Student student) {
        return studentMapper.selectList(student);
    }

    @Override
    public Student findBySno(Student student) {
        return studentMapper.findBySno(student);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Student student) {
        return studentMapper.update(student);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePassword(Student student) {
        return studentMapper.updatePassword(student);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Integer[] ids) {
        return studentMapper.deleteById(ids);
    }
}
