package com.hcp.controller;

import com.hcp.Utils.ResponseJsonUtils;
import com.hcp.interfaces.IOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 20:16 2018-06-03
 * @CreateBY : idea
 */
@Controller
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Resource
    IOrder order;

    @RequestMapping("ensureOrder")
    @ResponseBody
    public void ensureOrder(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入ensureOrder方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");
        String ticketId= request.getParameter("ticketId");
        Integer status = 0;
        Map<String,Object> ticketmap =order.ticketmap(ticketId);
        if (Integer.valueOf(ticketmap.get("counts").toString())>0){
            status =order.updateTicketNum(ticketId);
            if (status==1){
                Map<String,Object> trainMap = order.trainMap(ticketmap.get("trainId").toString());
                Integer seat =((Integer.valueOf(trainMap.get(ticketmap.get("seatType")+"Size").toString())-Integer.valueOf(ticketmap.get("counts").toString()))+1)% (Integer.valueOf(trainMap.get(ticketmap.get("seatType")+"JSize").toString()));
                if (seat==0){
                    seat=Integer.valueOf(trainMap.get(ticketmap.get("seatType")+"JSize").toString());
                }
                Integer jie =(int) Math.ceil(((Double.valueOf(trainMap.get(ticketmap.get("seatType")+"Size").toString())-Double.valueOf(ticketmap.get("counts").toString())))/ (Double.valueOf(trainMap.get(ticketmap.get("seatType")+"JSize").toString())));
                if (jie==0){
                    jie=1;
                }
                logger.info(jie+"节"+seat+"座");
                status = order.updateOrder(userId,ticketId,jie+"节"+seat+"座","0");
                if (status ==1){
                   status = order.updatAddOrderStatus(userId,ticketId,"1");
                    data.put("code",status);
                }else {
                    data.put("code",-1);
                }

            }else{
                data.put("code",-1);
            }
        }else {
            data.put("code",-2);
        }
        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("addOrder")
    @ResponseBody
    public void addOrder(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入addOrder方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");
        String ticketId= request.getParameter("ticketId");
        String type= request.getParameter("type");

        Integer status = 0;
        List<Map<String,Object>> addOrderList =order.queryAddOrderList(userId,type);
        for (Map<String,Object> addMap :addOrderList){
            if (addMap.get("ticketId").toString().equals(ticketId)){
            status=-2;
            }
        }
        if (status==0){
        status =order.updateAddOrder(userId,ticketId,type);
        }
        data.put("code",status);
        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("addOrderList")
    @ResponseBody
    public void addOrderList(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入addOrderList方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");
        String type= request.getParameter("type");

        Integer status = 0;
        List<Map<String,Object>> addOrderList =order.queryAddOrderList(userId,type);
        data.put("addOrderList",addOrderList);
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("completeOrder")
    @ResponseBody
    public void completeOrder(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入completeOrder方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");
        String ticketId= request.getParameter("ticketId");
        Integer status = 0;
        status = order.updateCompleteOrder(userId,ticketId,"1");
      data.put("code",status);
        ResponseJsonUtils.json(response, data);
    }


    @RequestMapping("changedOrder")
    @ResponseBody
    public void changedOrder(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入changedOrder方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");
        String ticketId= request.getParameter("ticketId");
        String type= request.getParameter("type");

        Integer status = 0;
        status = order.updatAddOrderStatus(userId,ticketId,type);

        data.put("code",status);
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("finishedOrderList")
    @ResponseBody
    public void finishedOrderList(HttpServletRequest request,HttpServletResponse response){
        logger.info("----------进入finishedOrderList方法---------");
        Map<String, Object> data = new HashMap<String, Object>();
        String userId =request.getParameter("userId");

        Integer status = 0;
        List<Map<String,Object>> finishedOrderList =order.queryFinishedOrderList(userId);
        data.put("finishedOrderList",finishedOrderList);
        ResponseJsonUtils.json(response, data);
    }

}
