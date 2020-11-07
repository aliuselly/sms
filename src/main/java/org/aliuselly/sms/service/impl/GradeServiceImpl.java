package org.aliuselly.sms.service.impl;

import org.aliuselly.sms.dao.GradeMapper;
import org.aliuselly.sms.domain.Grade;
import org.aliuselly.sms.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控年级信息
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GradeServiceImpl implements GradeService {

//    注入 mapper 接口对象
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public List<Grade> selectList(Grade grade) {
        return gradeMapper.selectList(grade);
    }

    @Override
    public List<Grade> selectAll() {
        return gradeMapper.selectAll();
    }

    @Override
    public Grade findByName(String gradeName) {
        return gradeMapper.findByName(gradeName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int insert(Grade grade) {
        return gradeMapper.insert(grade);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int update(Grade grade) {
        return gradeMapper.update(grade);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Integer[] ids) {
        return gradeMapper.deleteById(ids);
    }
}
