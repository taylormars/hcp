package com.hcp.service;

import com.hcp.controller.LoginController;
import com.hcp.interfaces.ITicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 17:48 2018-06-03
 * @CreateBY : idea
 */
@Service
public class TicketService implements ITicket{
    private Logger logger = LoggerFactory.getLogger(TicketService.class);
    @Resource
    private JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> queryStation() {
        logger.info("=========进入queryStation方法=======");
        String sql = "Select * from station ";
        try {
            List<Map<String, Object>> stationList = jdbcTemplate.queryForList(sql);
            return  stationList;
        }catch (Exception e){
            return null;
        }
    }

    public List<Map<String, Object>> queryTrainType() {
        logger.info("=========进入queryTrainTypen方法=======");
        String sql = "Select distinct trainType from train ";
        try {
            List<Map<String, Object>> trainTypeList = jdbcTemplate.queryForList(sql);
            return  trainTypeList;
        }catch (Exception e){
            return null;
        }
    }


    public List<Map<String, Object>> queryTicketList(String startStationId,String endStationId,String trainType) {
        logger.info("=========进入queryTicketList方法=======");
        String sql = "SELECT tk.ticketId, s.stationName AS startStationName, s2.stationName AS endStationName, tk.seatType, tk.price, tk.counts, tk.startTime, tk.endTime, tk.cc, tr.trainName, tr.trainType FROM ticket tk LEFT JOIN train tr ON tk.trainId = tr.trainId LEFT JOIN station s ON tk.startStationId = s.stationId LEFT JOIN station s2 ON tk.endStationId = s2.stationId";
        try {
            if(startStationId!=""){
                sql = sql+" WHERE tk.startStationId = '"+startStationId+"'";
                if (endStationId!=""){
                    sql = sql+" AND tk.endStationId = '"+endStationId+"'";
                }
                if (trainType!=""){
                    sql = sql+" AND tr.trainType = '"+trainType+"'";
                }
            }else if (endStationId!=""){
                sql = sql+" WHERE tk.endStationId = '"+endStationId+"'";
                if (trainType!=""){
                    sql = sql+" AND tr.trainType = '"+trainType+"'";
                }
            }else if (trainType!=""){
                sql = sql+" WHERE tr.trainType = '"+trainType+"'";
            }

            List<Map<String, Object>> trainTypeList = jdbcTemplate.queryForList(sql);
            return  trainTypeList;
        }catch (Exception e){
            return null;
        }
    }
}
