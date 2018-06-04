package com.hcp.controller;

import com.hcp.Utils.ResponseJsonUtils;
import com.hcp.interfaces.ITicket;
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
 * @Date: Created in 17:44 2018-06-03
 * @CreateBY : idea
 */
@Controller
public class TicketController {
    @Resource
    ITicket ticket;
    private Logger logger = LoggerFactory.getLogger(TicketController.class);

    @RequestMapping("/station")
    @ResponseBody
    void queryStatiom(HttpServletRequest request, HttpServletResponse response){
        logger.info("----------进入queryStatiom方法-------------");
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String,Object>> stationList =ticket.queryStation();
        data.put("stationList",stationList);
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("/trainType")
    @ResponseBody
    void queryTrainType(HttpServletRequest request, HttpServletResponse response){
        logger.info("----------进入queryTrainType方法-------------");
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String,Object>> trainTypeList =ticket.queryTrainType();
        data.put("trainTypeList",trainTypeList);
        ResponseJsonUtils.json(response, data);
    }

    @RequestMapping("/queryTickets")
    @ResponseBody
    void queryTickets(HttpServletRequest request, HttpServletResponse response){
        logger.info("----------进入queryTickets方法-------------");
        Map<String, Object> data = new HashMap<String, Object>();
        String startStationId =request.getParameter("startStationId");
        String endStationId =request.getParameter("endStationId");
        String trainType =request.getParameter("trainType");
        List<Map<String,Object>> ticketList =ticket.queryTicketList(startStationId,endStationId,trainType);
        data.put("ticketList",ticketList);
        ResponseJsonUtils.json(response, data);
    }




}
