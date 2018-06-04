package com.hcp.interfaces;

import com.hcp.model.UserMain;

import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 23:06 2018-06-02
 * @CreateBY : idea
 */
public interface ILogin {

    Map<String, Object> queryLoadingInfo(String userId);

    Integer insertUserInfo(UserMain userMain);

    UserMain queryUserInfo(UserMain userMain);
    Integer queryUserInfoByRegInfo(UserMain userMain);

}
