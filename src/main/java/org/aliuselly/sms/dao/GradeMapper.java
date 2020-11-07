package org.aliuselly.sms.dao;

import org.aliuselly.sms.domain.Grade;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操控年级信息
 */
@Repository
public interface GradeMapper {

    /**
     * 根据年级名称查询指定/全部年级信息列表
     * @param grade
     * @return
     */
    @Select({
            "<script>",
                "select id, name, manager, email, telephone, introducation",
                " from tb_grade",
                "<where>",
                    "<if test='name != null and name != \"\"'>",
                        " and name like concat(concat('%', #{name}), '%')",
                    "</if>",
                "</where>",
            "</script>"
    })
    List<Grade> selectList(Grade grade);

    /**
     * 查询所有年级信息列表（用于在班级管理页面中获取年级信息）
     * @return
     */
    @Select("select id, name, manager, email, telephone, introducation" +
            " from tb_grade")
    List<Grade> selectAll();

    /**
     * 根据年级名称查询指定的年级信息
     * @param gradeName
     * @return
     */
    @Select("select id, name, manager, email, telephone, introducation" +
            " from tb_grade" +
            " where name = #{gradeName}")
    Grade findByName(String gradeName);

    /**
     * 添加年级信息
     * @param grade
     * @return
     */
    @Insert("insert into tb_grade(name, manager, email, telephone, introducation)" +
            " values(#{name}, #{manager}, #{email}, #{telephone}, #{introducation})")
    int insert(Grade grade);

    /**
     * 根据 id 修改指定年级信息
     * @param grade
     * @return
     */
    @Update("update tb_grade" +
            " set name = #{name}," +
            " manager = #{manager}," +
            " email = #{email}," +
            " telephone = #{telephone}," +
            " introducation = #{introducation}" +
            " where id = #{id}")
    int update(Grade grade);

    /**
     * 根据 id 删除指定年级信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
                "delete from tb_grade",
                "where id in",
                "<foreach collection='array' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                "</foreach>",
            "</script>"
    })
    int deleteById(Integer[] ids);
}
