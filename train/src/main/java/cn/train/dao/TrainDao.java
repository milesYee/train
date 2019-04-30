package cn.train.dao;


import java.util.List;
import java.util.Map;

import cn.train.entity.Traininfo;
import cn.train.entity.User;

/*
 * @ Copyright (c) Create by JASON  Date:2018-04-17  All rights reserved.
 *
 * @ class description：火车DAO接口
 *
 */
public interface TrainDao {
	
	public List<Traininfo> getTraininfoList();
	
	public Traininfo getTraininfoDetail(int trainNo);

	public int deleteTraininfoById(int id);
	
	public int addTraininfo(Traininfo trainInfo);

	public List<Map> getTicketList();

	public Map getTicketOrderByUser(String UserName);

	public void updateTicketOrder(Map map);

	public void updateTrainInfo(Map map);

	public void addTicketOrder(Map map);
}


