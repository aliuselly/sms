package org.aliuselly.sms.service;

import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Teacher;

import java.util.List;

/**
 * 业务层-操控教师信息
 */
public interface TeacherService {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    Teacher login(LoginForm loginForm);

    /**
     * 根据教师与班级名查询指定/全部教师信息列表
     * @param teacher
     * @return
     */
    List<Teacher> selectList(Teacher teacher);

    /**
     * 根据工号查询指定教师信息
     * @param teacher
     * @return
     */
    Teacher findByTno(Teacher teacher);

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    int insert(Teacher teacher);

    /**
     * 根据 id 修改指定教师信息
     * @param teacher
     * @return
     */
    int update(Teacher teacher);

    /**
     * 根据 id 删除指定教师信息
     * @param ids
     * @return
     */
    int deleteById(Integer[] ids);

    /**
     * 根据 id 修改指定教师密码
     * @param teacher
     * @return
     */
    int updatePassword(Teacher teacher);
}
