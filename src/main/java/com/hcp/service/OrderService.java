package com.hcp.service;

import com.hcp.interfaces.IOrder;
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
 * @Date: Created in 20:23 2018-06-03
 * @CreateBY : idea
 */
@Service
public class OrderService implements IOrder{
    private Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> ticketmap(String ticketId) {
        logger.info("=========进入ticketmap方法=======");
        String sql = "Select * from ticket where ticketid =' "+ticketId+"'";
        try {
          Map<String, Object> ticketmap = jdbcTemplate.queryForMap(sql);
            return  ticketmap;
        }catch (Exception e){
            return null;
        }
    }

    public Integer updateTicketNum(String ticketId) {
        logger.info("=========进入updateTicketNum方法=======");
        String sql = "UPDATE ticket SET counts =counts -1 WHERE ticketId = '"+ticketId+"'";
        try {
            jdbcTemplate.update(sql);
            return  1;
        }catch (Exception e){
            return 0;
           }
        }

    public Map<String, Object> trainMap(String trainId) {
        logger.info("=========进入trainMap方法=======");
        String sql = "Select * from train where trainId =' "+trainId+"'";
        try {
            Map<String, Object> trainMap = jdbcTemplate.queryForMap(sql);
            return  trainMap;
        }catch (Exception e){
            return null;
        }
    }

    public Integer updateOrder(String userId,String ticketId,String seatNum,String status) {
        logger.info("=========进入updateOrder方法=======");
        String sql = "INSERT INTO order_ensure (userId,ticketId,seatNum,status) VALUES (?,?,?,?)";
        try {
            jdbcTemplate.update(sql,new Object[]{Integer.valueOf(userId),Integer.valueOf(ticketId),seatNum,Integer.valueOf(status)});
            return  1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public Integer updateAddOrder(String userId,String ticketId,String type) {
        logger.info("=========进入updateOrder方法=======");
        String sql = "INSERT INTO order_add (userId,ticketId,status) VALUES (?,?,?)";
        try {
            jdbcTemplate.update(sql,new Object[]{Integer.valueOf(userId),Integer.valueOf(ticketId),Integer.valueOf(type)});
            return  1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<Map<String, Object>> queryAddOrderList(String userId , String type) {
        logger.info("=========进入queryAddOrderList方法=======");
            String sql = "SELECT tk.ticketId, s.stationName AS startStationName, s2.stationName AS endStationName, tk.seatType, tk.price, tk.counts, tk.startTime, tk.endTime, tk.cc, tr.trainName, tr.trainType FROM ticket tk LEFT JOIN train tr ON tk.trainId = tr.trainId LEFT JOIN station s ON tk.startStationId = s.stationId LEFT JOIN station s2 ON tk.endStationId = s2.stationId WHERE tk.ticketId IN ( SELECT ticketId FROM order_add WHERE status= "+type+" AND userId = "+userId+" )";
        try {
            List<Map<String, Object>> addOrderList = jdbcTemplate.queryForList(sql);
            return  addOrderList;
        }catch (Exception e){
            return null;
        }
    }

    public Integer updateCompleteOrder(String userId, String ticketId, String status) {
        logger.info("=========进入updateCompleteOrder方法=======");
        String sql = "UPDATE order_ensure SET status ="+status+ "WHERE ticketId = '"+ticketId+"' AND  userId ="+userId;
        try {
            jdbcTemplate.update(sql);
            return  1;
        }catch (Exception e){
            return 0;
        }
    }


    public Integer updatAddOrderStatus(String userId, String ticketId, String type) {
        logger.info("=========进入updatAddOrderStatus方法=======");
        String sql = "UPDATE order_add SET status ="+type+2+ " WHERE ticketId = '"+ticketId+"' AND  userId ="+userId +" AND status ="+type;
        try {
            jdbcTemplate.update(sql);
            return  1;
        }catch (Exception e){
            return 0;
        }
    }

    public List<Map<String, Object>> queryFinishedOrderList(String userId) {
        logger.info("=========进入queryAddOrderList方法=======");
        String sql = "SELECT tk.ticketId, s.stationName AS startStationName, s2.stationName AS endStationName, tk.seatType, tk.price, tk.counts, tk.startTime, tk.endTime, tk.cc, tr.trainName, tr.trainType FROM ticket tk LEFT JOIN train tr ON tk.trainId = tr.trainId LEFT JOIN station s ON tk.startStationId = s.stationId LEFT JOIN station s2 ON tk.endStationId = s2.stationId WHERE tk.ticketId IN ( SELECT ticketId FROM order_ensure WHERE status= 1 AND userId = "+userId+" )";
        try {
            List<Map<String, Object>> finishedOrderList = jdbcTemplate.queryForList(sql);
            return  finishedOrderList;
        }catch (Exception e){
            return null;
        }
    }

}
