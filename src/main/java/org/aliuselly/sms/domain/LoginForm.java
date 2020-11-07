package org.aliuselly.sms.domain;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 用户登录表单信息
 */
@Alias("loginForm")
public class LoginForm implements Serializable {

    private static final long serialVersionUID = 2711381169811955607L;

    private String username;
    private String password;
    private String verifyCode;
    private Integer userType;

    @Override
    public String toString() {
        return "LoginForm{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", userType=" + userType +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
