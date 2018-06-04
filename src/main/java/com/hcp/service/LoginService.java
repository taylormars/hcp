package com.hcp.service;

import com.hcp.interfaces.ILogin;
import com.hcp.model.UserMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 23:01 2018-06-02
 * @CreateBY : idea
 */
@Service
public class LoginService implements ILogin{
    @Resource
    private JdbcTemplate jdbcTemplate;
    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public Map<String, Object> queryLoadingInfo(String userId) {
        logger.info("adasd");
        String sql = "SELECT IFNULL(ap.petKindId, 0) AS petkindid, um.userName, um.coin, um.diamond, um.userPicId, COUNT(ap.petId) AS isNew ,ap.petStatus ,ap.petNickName FROM adopt ap, user_main um WHERE ap.userId = um.userId AND um.userId = ?";
        Map map = new HashMap();
        try {
            map = jdbcTemplate.queryForMap(sql, new Object[]{userId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Integer insertUserInfo(UserMain userMain) {
        logger.info("===============进入insertUserInfo方法==============");
        String sql = "Insert Into user_main (userName,password,mobile,realName,idCard,sex,address) Values(?,?,?,?,?,?,?)";
        try {

            jdbcTemplate.update(sql,new Object[]{userMain.getUserName(),userMain.getPassword(),userMain.getMobile(),userMain.getRealName(),userMain.getIdCard(),userMain.getSex(),userMain.getAddress()});
            return 1;
        }catch (Exception e ){
            e.printStackTrace();
            return  0;
        }
    }

    public UserMain queryUserInfo(UserMain userMain) {
        logger.info("===============进入queryUserInfo方法==============");
        String sql = "SELECT * FROM user_main WHERE userName = ? AND password = ?";
        try {

          UserMain userMain1=  jdbcTemplate.queryForObject(sql,new UserMain(),new Object[]{userMain.getUserName(),userMain.getPassword()});
            return userMain1;
        }catch (Exception e ){
            e.printStackTrace();
            return  null;
        }
    }

    public Integer queryUserInfoByRegInfo(UserMain userMain) {
        logger.info("===============进入queryUserInfoByUserName方法==============");
        String sql1 = "SELECT * FROM user_main WHERE userName = ? ";
        String sql2 = "SELECT * FROM user_main WHERE mobile = ? ";
        String sql3 = "SELECT * FROM user_main WHERE idCard = ? ";

        try {


            List<Map<String,Object>> userMain1=  jdbcTemplate.queryForList(sql1,new Object[]{userMain.getUserName()});
           if (userMain1.size()==0){
               List<Map<String,Object>>userMain2=  jdbcTemplate.queryForList(sql2,new Object[]{userMain.getMobile()});
               if (userMain2.size()==0){
                   List<Map<String,Object>>userMain3=  jdbcTemplate.queryForList(sql3,new Object[]{userMain.getIdCard()});
                    if (userMain3.size()==0){
                        return  1;
                    }else {
                        return -3;
                    }
               }else {
                   return -2;
               }
           }else {
               return -1;
           }
        }catch (Exception e ){
            e.printStackTrace();
            return  0;
        }
    }
}
