package org.aliuselly.sms.service;

import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Student;

import java.util.List;

/**
 * 业务层-操控学生信息
 */
public interface StudentService {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    Student login(LoginForm loginForm);

    /**
     * 根据班级与学生名获取指定或全部学生信息列表
     * @param student
     * @return
     */
    List<Student> selectList(Student student);

    /**
     * 根据学号获取指定学生信息
     * @param student
     * @return
     */
    Student findBySno(Student student);

    /**
     * 添加班级信息
     * @param student
     * @return
     */
    int insert(Student student);

    /**
     * 根据 id 修改指定学生信息
     * @param student
     * @return
     */
    int update(Student student);

    /**
     * 根据 id 修改指定学生密码
     * @param student
     * @return
     */
    int updatePassword(Student student);

    /**
     * 根据 id 删除指定学生信息
     * @param ids
     * @return
     */
    int deleteById(Integer[] ids);
}
