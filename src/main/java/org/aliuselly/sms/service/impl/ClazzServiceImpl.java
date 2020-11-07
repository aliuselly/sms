package org.aliuselly.sms.service.impl;

import org.aliuselly.sms.dao.ClazzMapper;
import org.aliuselly.sms.domain.Clazz;
import org.aliuselly.sms.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层实现类-操控班级信息
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ClazzServiceImpl implements ClazzService {

//    注入 mapper 接口对象
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Clazz> selectList(Clazz clazz) {
        return clazzMapper.selectList(clazz);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Clazz> selectAll() {
        return clazzMapper.selectAll();
    }

    @Override
    public int insert(Clazz clazz) {
        return clazzMapper.insert(clazz);
    }

    @Override
    public int deleteById(Integer[] ids) {
        return clazzMapper.deleteById(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Clazz findByName(String name) {
        return clazzMapper.findByName(name);
    }

    @Override
    public int update(Clazz clazz) {
        return clazzMapper.update(clazz);
    }
}
