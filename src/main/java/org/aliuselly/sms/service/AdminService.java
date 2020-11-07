package org.aliuselly.sms.service;

import org.aliuselly.sms.domain.Admin;
import org.aliuselly.sms.domain.LoginForm;

import java.util.List;

/**
 * 业务层-操控管理员信息
 */
public interface AdminService {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    Admin login(LoginForm loginForm);

    /**
     * 根据用户名查询指定管理员信息
     * @param name
     * @return
     */
    Admin findByName(String name);

    /**
     * 添加管理员信息
     * @param admin
     * @return
     */
    int insert(Admin admin);

    /**
     * 根据姓名查询指定/所有管理员信息列表
     * @param admin
     * @return
     */
    List<Admin> selectList(Admin admin);

    /**
     * 根据 id 更新指定管理员信息
     * @param admin
     * @return
     */
    int update(Admin admin);

    /**
     * 根据 id 修改指定用户密码
     * @param admin
     * @return
     */
    int updatePassword(Admin admin);

    /**
     * 根据 id 删除管理员信息
     * @param ids
     * @return
     */
    int deleteById(Integer[] ids);
}
