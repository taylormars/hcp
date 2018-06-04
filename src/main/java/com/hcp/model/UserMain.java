package com.hcp.model;


import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 11:44 2018-06-03
 * @CreateBY : idea
 */
public class UserMain implements RowMapper<UserMain>, Serializable {
    private String userName;
    private String userId;
    private String password;
    private String realName;
    private String sex;
    private String address;
    private String mobile;
    private String idCard;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public UserMain mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMain userMain = new UserMain();
        userMain.setUserId(rs.getString("userId"));
        userMain.setUserName(rs.getString("userName"));
        userMain.setPassword(rs.getString("password"));
        userMain.setRealName(rs.getString("realName"));
        userMain.setAddress(rs.getString("address"));
        userMain.setSex(rs.getString("sex"));
        userMain.setMobile(rs.getString("mobile"));
        userMain.setIdCard(rs.getString("idCard"));
        return userMain;
    }
}
