package cn.train.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.train.entity.User;
import cn.train.util.RedisClientPool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import cn.train.dao.TrainDao;
import cn.train.entity.Traininfo;
import cn.train.service.TrainService;

/*
 * @ Copyright (c) Create by JASON  Date:2018-04-17  All rights reserved.
 *
 * @ class description：火车信息service实现类，业务处理
 *
 */
@Service("trainService")
public class TrainServiceImpl implements TrainService {
	@Resource 
    private TrainDao trainDao;
	@Resource
	private RedisTemplate redisTemplate;

	@Override
	public List<Traininfo> getTraininfoList() throws Exception{
		return trainDao.getTraininfoList();
	}

	@Override
	public Traininfo getTraininfoDetail(int trainNo) throws Exception{
		return trainDao.getTraininfoDetail(trainNo);
	}
	
	
	//此方法已支持事务，如果有一个删除不成功就会全部回滚
	@Override
	public int deleteTraininfoByids(String[] selectIds) throws Exception{
		int num = 0;
		for(int i = 0; i < selectIds.length; i++){
			num += trainDao.deleteTraininfoById(Integer.parseInt(selectIds[i]));
		}
		return num;
	}
	
	@Override
	public int addTraininfo(Traininfo trainInfo) throws Exception{
		return trainDao.addTraininfo(trainInfo);
	}

	@Override
	public List<Map> getTicketList() {
		List<Map> ticketList;
		Object tickets = redisTemplate.opsForValue().get("ticketList");
		ticketList=(List<Map>)tickets;
		if(ticketList==null||ticketList.size()==0){
			ticketList = trainDao.getTicketList();
			redisTemplate.opsForValue().set("ticketList",ticketList);
		}
		return ticketList;
	}

	@Override
	public Map getTicketOrderByUser(String userName) {
		return trainDao.getTicketOrderByUser(userName);
	}

	@Override
	public void updateTicketOrder(Map map) {
		trainDao.updateTicketOrder(map);
		List<Map> ticketList = trainDao.getTicketList();
		redisTemplate.delete("ticketList");
		redisTemplate.opsForValue().set("ticketList",ticketList);
	}

	@Override
	public void updateTrainInfo(Map map) {
		trainDao.updateTrainInfo(map);
	}

	@Override
	public void addTicketOrder(Map map) {
		trainDao.addTicketOrder(map);
		redisTemplate.delete("ticketList");
	}


}


