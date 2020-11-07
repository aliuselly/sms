package org.aliuselly.sms.service;

import org.aliuselly.sms.domain.Grade;

import java.util.List;

/**
 * 业务层-操控年级信息
 */
public interface GradeService {

    /**
     * 根据年级名称查询指定/全部年级列表信息
     * @param grade
     * @return
     */
    List<Grade> selectList(Grade grade);

    /**
     * 查询所有年级列表信息(用于班级管理页面中获取年级信息)
     * @return
     */
    List<Grade> selectAll();

    /**
     * 根据年级名称查询指定年级信息
     * @param gradeName
     * @return
     */
    Grade findByName(String gradeName);

    /**
     * 添加年级信息
     * @param grade
     * @return
     */
    int insert(Grade grade);

    /**
     * 根据 id 修改指定年级信息
     * @param grade
     * @return
     */
    int update(Grade grade);

    /**
     * 根据 id 删除指定年级信息
     * @param ids
     * @return
     */
    int deleteById(Integer[] ids);
}
