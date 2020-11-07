package org.aliuselly.sms.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 年级以及年级班主任信息
 */
@Alias("grade")
public class Grade implements Serializable {

    private static final long serialVersionUID = 5068879936478411357L;

//    年级信息
    private Integer id;
    private String name;
    private String introducation;
//    年级班主任信息
    private String manager;
    private String email;
    private String telephone;

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introducation='" + introducation + '\'' +
                ", manager='" + manager + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
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

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
