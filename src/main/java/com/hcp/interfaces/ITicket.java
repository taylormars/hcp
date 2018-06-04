package com.hcp.interfaces;

import java.util.List;
import java.util.Map;

/**
 * @Author : Liyutong
 * @Description ：
 * @Date: Created in 17:47 2018-06-03
 * @CreateBY : idea
 */
public interface ITicket {

    List<Map<String,Object>> queryStation();

    List<Map<String,Object>> queryTrainType();

    List<Map<String,Object>> queryTicketList(String startStationId,String endStationId,String trainType );

}
