package org.aliuselly.sms.service;

import org.aliuselly.sms.domain.Clazz;

import java.util.List;

/**
 * 业务层-操控班级信息
 */
public interface ClazzService {

    /**
     * 根据年级与班级名查询指定/全部班级信息列表
     * @param clazz
     * @return
     */
    List<Clazz> selectList(Clazz clazz);

    /**
     * 查询所有班级信息列表(用于在学生管理页面中获取班级信息)
     * @return
     */
    List<Clazz> selectAll();

    /**
     * 添加班级信息
     * @param clazz
     * @return
     */
    int insert(Clazz clazz);

    /**
     * 根据 id 删除指定班级信息
     * @param ids
     * @return
     */
    int deleteById(Integer[] ids);

    /**
     * 根据班级名获取指定班级信息
     * @param name
     * @return
     */
    Clazz findByName(String name);

    /**
     * 根据 id 修改指定班级信息
     * @param clazz
     * @return
     */
    int update(Clazz clazz);
}
