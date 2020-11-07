package org.aliuselly.sms.dao;

import org.aliuselly.sms.domain.LoginForm;
import org.aliuselly.sms.domain.Student;
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
public interface StudentMapper {

    /**
     * 验证登录信息是否正确
     * @param loginForm
     * @return
     */
    @Select("select id, sno, name, gender, password, portrait_path, email, telephone, address, introducation, clazz_name" +
            " from tb_student" +
            " where name = #{username} and password = #{password}")
    Student login(LoginForm loginForm);

    /**
     * 根据班级与学生名获取指定或全部学生信息列表
     * @param student
     * @return
     */
    @Select({
            "<script>",
                "select id, sno, name, gender, password, portrait_path, email, telephone, address, introducation, clazz_name",
                " from tb_student ",
                "<where>",
                    "<if test='clazz_name != null and clazz_name != \"\"'> and clazz_name = #{clazz_name}</if>",
                    "<if test='name != null and name != \"\"'> and name like concat(concat('%', #{name}), '%')</if>",
                "</where>",
            "</script>"
    })
    List<Student> selectList(Student student);

    /**
     * 根据学号查询指定学生信息
     * @param student
     * @return
     */
    @Select("select id, sno, name, gender, password, portrait_path, email, telephone, address, introducation, clazz_name" +
            " from tb_student" +
            " where sno = #{sno}")
    Student findBySno(Student student);

    /**
     * 添加学生信息
     * @param student
     * @return
     */
    @Insert("insert into tb_student(sno, name, gender, password, portrait_path, email, telephone, address, introducation, clazz_name)" +
            " values(#{sno}, #{name}, #{gender}, #{password}, #{portrait_path}, #{email}, #{telephone}, #{address}, #{introducation}, #{clazz_name})")
    int insert(Student student);

    /**
     * 根据 id 修改指定学生信息
     * @param student
     * @return
     */
    @Update({
            "<script>",
                "update tb_student ",
                "<set>",
                    "<if test='name != null and name != \"\"'> name = #{name},</if>",
                    "<if test='gender != null and gender != \"\"'> gender = #{gender},</if>",
                    "<if test='password != null and password != \"\"'> password = #{password},</if>",
                    "<if test='email != null and email != \"\"'> email = #{email},</if>",
                    "<if test='telephone != null and telephone != \"\"'> telephone = #{telephone},</if>",
                    "<if test='address != null and address != \"\"'> address = #{address},</if>",
                    "<if test='introducation != null and introducation != \"\"'> introducation = #{introducation},</if>",
                    "<if test='portrait_path != null and portrait_path != \"\"'> portrait_path = #{portrait_path},</if>",
                    "<if test='clazz_name != null and clazz_name != \"\"'> clazz_name = #{clazz_name}</if>",
                "</set>",
                "where id = #{id}",
            "</script>"
    })
    int update(Student student);

    /**
     * 根据 id 修改指定学生密码
     * @param student
     * @return
     */
    @Update("update tb_student" +
            " set password = #{password}" +
            " where id = #{id}")
    int updatePassword(Student student);

    /**
     * 根据 id 删除指定学生信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
                "delete from tb_student",
                " where id in ",
                "<foreach collection='array' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                "</foreach>",
            "</script>"
    })
    int deleteById(Integer[] ids);
}
