package org.aliuselly.sms.dao;

import org.aliuselly.sms.domain.Admin;
import org.aliuselly.sms.domain.LoginForm;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作管理员信息
 */
@Repository
public interface AdminMapper {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    @Select("select id, name, gender, password, email, telephone, address, portrait_path" +
            " from tb_admin" +
            " where name = #{username} and password = #{password}")
    Admin login(LoginForm loginForm);

    /**
     * 通过姓名查询制定管理员信息
     * @param name
     * @return
     */
    @Select("select id, name, gender, password, email, telephone, address, portrait_path" +
            " from tb_admin" +
            " where name = #{name}")
    Admin findByName(String name);

    /**
     * 添加管理员信息
     *
     * 通过这里我知道了
     * mybatis 的 cud 是可以有结果返回的
     * @param admin
     * @return
     */
    @Insert("insert into tb_admin(name, gender, password, email, telephone, address, portrait_path)" +
            " values(#{name}, #{gender}, #{password}, #{email}, #{telephone}, #{address}, #{portrait_path})")
    int insert(Admin admin);

    /**
     * 根据姓名查询指定/所有管理员信息列表
     *
     * 如果 "''" 了，还要用 “” 的话，那么就是要反义了
     * 即，" ' \"\" ' "
     * @param admin
     * @return
     */
    @Select({
            "<script>",
                "select id, name, gender, password, email, telephone, address, portrait_path",
                " from tb_admin ",
                "<where>",
                    "<if test='name != null and name != \"\"'>",
                        " and name like concat(concat('%', #{name}), '%')",
                    "</if>",
                "</where>",
            "</script>"
    })
    List<Admin> selectList(Admin admin);

    /**
     * 根据 id 更新指定管理员信息
     *
     * 注意，if 标签是要在里面使用的，即 where 或者 set 之类的
     * 还有，这里除了密码，id 以外其他的都是更新了
     * 单独弄了一个更新密码的方法出来
     * 因为呢，更新密码是去到一个新的页面上更改的
     * 最好弄一个新的方法比较好，毕竟是重要的数据
     * @param admin
     * @return
     */
    @Update({
            "<script>",
                "update tb_admin",
                "<set>",
                    "<if test='name != null and name != \"\"'> name = #{name},</if>",
                    "<if test='gender != null and gender != \"\"'> gender = #{gender},</if>",
                    "<if test='email != null and email != \"\"'> email = #{email},</if>",
                    "<if test='telephone != null and telephone != \"\"'> telephone = #{telephone},</if>",
                    "<if test='address != null and address != \"\"'> address = #{address},</if>",
                    "<if test='portrait_path != null and portrait_path != \"\"'> portrait_path = #{portrait_path}</if>",
                "</set>",
                " where id = #{id}",
            "</script>"
    })
    int update(Admin admin);

    /**
     * 根据 id 修改指定管理员密码
     * @param admin
     * @return
     */
    @Update("update tb_admin" +
            " set password = #{password}" +
            " where id = #{id}")
    int updatePassword(Admin admin);

    /**
     * 根据 id 删除指定管理员信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
                "delete from tb_admin",
                " where id in",
                "<foreach collection='array' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                "</foreach>",
            "</script>"
    })
    int deleteById(Integer[] ids);
}
