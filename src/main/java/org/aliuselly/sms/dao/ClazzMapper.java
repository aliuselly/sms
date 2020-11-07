package org.aliuselly.sms.dao;

import org.aliuselly.sms.domain.Clazz;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操控班级信息
 */
@Repository
public interface ClazzMapper {

    /**
     * 根据班级名称获取指定/全部班级信息列表
     * @param clazz
     * @return
     */
    @Select({
            "<script>",
                "select id, name, number, introducation, coordinator, email, telephone, grade_name",
                " from tb_clazz ",
                "<where>",
                    "<if test='grade_name != null and grade_name != \"\"'> and grade_name = #{grade_name}</if>",
                    "<if test='name != null and name != \"\"'> and name like concat(concat('%', #{name}), '%')</if>",
                "</where>",
            "</script>"
    })
    List<Clazz> selectList(Clazz clazz);

    /**
     * 查询所有班级列表信息（用于学生管理页面中获取班主任信息）
     * @return
     */
    @Select("select id, name, number, introducation, coordinator, email, telephone, grade_name" +
            " from tb_clazz")
    List<Clazz> selectAll();

    /**
     * 获取指定名称的班级信息
     * @param name
     * @return
     */
    @Select("select id, name, number, introducation, coordinator, email, telephone, grade_name" +
            " from tb_clazz" +
            " where name = #{name}")
    Clazz findByName(String name);

    /**
     * 添加班级信息
     * @param clazz
     * @return
     */
    @Insert("insert into tb_clazz(name, number, introducation, coordinator, email, telephone, grade_name)" +
            " values(#{name}, #{number}, #{introducation}, #{coordinator}, #{email}, #{telephone}, #{grade_name})")
    int insert(Clazz clazz);

    /**
     * 根据 id 删除指定班级信息
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
                "delete from tb_clazz",
                " where id in",
                "<foreach collection='array' item='id' open='(' separator=',' close=')'>",
                    "#{id}",
                "</foreach>",
            "</script>"
    })
    int deleteById(Integer[] ids);

    /**
     * 根据 id 修改指定班级信息
     *
     * 这种写法呢，和 adminMapper 那个呢
     * 还是 adminMapper 那个好，能够随机应变
     * 同时呢，如果没有的属性，也是减少了负担
     * 对于多数据而言，但是呢，现在是少数据
     * 不过呢，一般游览器那边都是填充好原来的数据的
     * 然后提交上来的，除非，有特殊设置
     * 感觉一般都是使用下面这个多点，因为呢
     * 保存后，想修改，就是读取原来的数据
     * 然后封装提交给服务器的，除非你做了处理
     * 原来相同的数据去掉
     * @param clazz
     * @return
     */
    @Update("update tb_clazz" +
            " set name = #{name}," +
            " number = #{number}," +
            " introducation = #{introducation}," +
            " coordinator = #{coordinator}," +
            " email = #{email}," +
            " telephone = #{telephone}," +
            " grade_name = #{grade_name}" +
            " where id = #{id}")
    int update(Clazz clazz);
}
