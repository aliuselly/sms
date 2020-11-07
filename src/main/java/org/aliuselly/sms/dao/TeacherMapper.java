package org.aliuselly.sms.dao;

import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操控学生信息
 */
@Repository
public interface TeacherMapper {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    @Select("select id, tno, name, gender, password, email, telephone, address, portrait_path, clazz_name" +
            " from tb_teacher" +
            " where name = #{username} and password = #{password}")
    Teacher login(LoginForm loginForm);

    /**
     * 根据教师与班级名查询指定/全部教师信息列表
     * @param teacher
     * @return
     */
    @Select({
            "<script>",
                "select id, tno, name, gender, password, email, telephone, address, portrait_path, clazz_name",
                " from tb_teacher",
                "<where>",
                    "<if test='clazz_name != null'> and clazz_name = #{clazz_name}</if>",
                    "<if test='name != null'> and name like concat(concat('%', #{name}), '%')</if>",
                "</where>",
            "</script>"
    })
    List<Teacher> selectList(Teacher teacher);

    /**
     * 根据工号查询指定教师信息
     * @param teacher
     * @return
     */
    @Select("select id, tno, name, gender, password, email, telephone, address, portrait_path, clazz_name" +
            " from tb_teacher" +
            " where tno = #{tno}")
    Teacher findByTno(Teacher teacher);

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    @Insert("insert into tb_teacher(tno, name, gender, password, email, telephone, address, portrait_path, clazz_name)" +
            " values(#{tno}, #{name}, #{gender}, #{password}, #{email}, #{telephone}, #{address}, #{portrait_path}, #{clazz_name})")
    int insert(Teacher teacher);

    /**
     * 根据 id 修改指定教师信息
     * @param teacher
     * @return
     */
    @Update({
            "<script>",
                "update tb_teacher",
                "<set>",
                    "<if test='name != null and name != \"\"'> name = #{name},</if>",
                    "<if test='gender != null and gender != \"\"'> gender = #{gender},</if>",
                    "<if test='password != null and password != \"\"'> password = #{password},</if>",
                    "<if test='email != null and email != \"\"'> email = #{email},</if>",
                    "<if test='telephone != null and telephone != \"\"'> telephone = #{telephone},</if>",
                    "<if test='address != null and address != \"\"'> address = #{address},</if>",
                    "<if test='portrait_path != null and portrait_path != \"\"'> portrait_path = #{portrait_path},</if>",
                    "<if test='clazz_name != null and clazz_name != \"\"'> clazz_name = #{clazz_name}</if>",
                "</set>",
                "where id = #{id}",
            "</script>"
    })
    int update(Teacher teacher);

    /**
     * 根据 id 修改指定教师密码
     * @param teacher
     * @return
     */
    @Update("update tb_teacher" +
            " set password = #{password}" +
            " where id = #{id}")
    int updatePassword(Teacher teacher);

    /**
     * 根据 id 删除指定教师信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
                "delete from tb_teacher",
                " where id in ",
                "<foreach collection='array' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                "</foreach>",
            "</script>"
    })
    int deleteById(Integer[] ids);
}
