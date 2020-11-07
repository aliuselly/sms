package org.aliuselly.sms.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 班级信息
 *
 * 感觉设计思路就是
 * 一个班级，有班级信息，还有所属管理人信息，还有上级信息
 */
@Alias("clazz")
public class Clazz implements Serializable {

    private static final long serialVersionUID = -81217865549235868L;

//    班级信息
    private Integer id;
    private String name;
    private String number;
    private String introducation;
//    班主任信息
    private String coordinator;
    private String telephone;
    private String email;
//    所属年纪
    private String grade_name;

    public Clazz(String name, String grade_name) {
        this.name = name;
        this.grade_name = grade_name;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", introducation='" + introducation + '\'' +
                ", coordinator='" + coordinator + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", grade_name='" + grade_name + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }
}
