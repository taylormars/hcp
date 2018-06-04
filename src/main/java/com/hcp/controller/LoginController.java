package com.hcp.controller;

import com.hcp.Utils.ResponseJsonUtils;
import com.hcp.interfaces.ILogin;
import com.hcp.model.UserMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 23:07 2018-06-02
 * @CreateBY : idea
 */
@Controller
public class LoginController {
    @Resource
    ILogin login;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/login")
    @ResponseBody
   public void   queryLoadingInfo(HttpServletRequest request , HttpServletResponse response){
        logger.info("-----------进入login方法------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (userName!=null&&password!=null) {
            UserMain userMain = new UserMain();
            userMain.setUserName(userName);
            userMain.setPassword(password);
            UserMain userMain2  = login.queryUserInfo(userMain);
            if (userMain2!=null){
                data.put("userId",userMain2.getUserId());
                data.put("code",1);
            }else {
                data.put("code",0);
            }
        }
        logger.info("-----------login方法end-----");
        ResponseJsonUtils.json(response, data);

    }

    @RequestMapping("/register")
    @ResponseBody
    public void   register(HttpServletRequest request , HttpServletResponse response){
        logger.info("-----------进入register方法------");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String realName = request.getParameter("realName");
        String sex = request.getParameter("sex");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String idCard = request.getParameter("idCard");

        Integer status = 0;
        Map<String, Object> data = new HashMap<String, Object>();
            UserMain userMain = new UserMain();
            try {
                userMain.setUserName(userName);
                userMain.setPassword(password);
                userMain.setRealName(realName);
                userMain.setSex(sex);
                userMain.setAddress(address);
                userMain.setMobile(mobile);
                userMain.setIdCard(idCard);
            }catch (Exception e){
                e.printStackTrace();
                data.put("code",0);
            }

            status = login.queryUserInfoByRegInfo(userMain);
            if (status==1){
            status = login.insertUserInfo(userMain);
            }
        data.put("code",status);
        logger.info("-----------register方法end-----");

        ResponseJsonUtils.json(response, data);

    }
}
