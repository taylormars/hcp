package com.hcp.interfaces;

import java.util.List;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 20:22 2018-06-03
 * @CreateBY : idea
 */
public interface IOrder {
    Map<String , Object> ticketmap (String  ticketId);

   Integer updateTicketNum (String ticketId);

    Map<String , Object> trainMap (String  trainId);

    Integer updateOrder (String userId,String ticketId,String seatNum,String status);

    Integer updateAddOrder (String userId,String ticketId,String type);

    List<Map<String,Object>> queryAddOrderList(String userId,String type );

    Integer updateCompleteOrder (String userId,String ticketId,String status);

    Integer updatAddOrderStatus (String userId,String ticketId,String type);

    List<Map<String,Object>> queryFinishedOrderList(String userId );


}
