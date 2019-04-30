package cn.train.controller;

import cn.train.entity.Traininfo;
import cn.train.entity.User;
import cn.train.service.TrainService;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @ Copyright (c) Create by JASON  Date:2018-04-17  All rights reserved.
 *
 * @ class description：火车票控制
 *
 */
@Controller
public class TicketController {

	@Resource
	private AmqpTemplate amqpTemplate;

	@Resource
	@Qualifier("trainService")
	private TrainService trainService;
	
	//火车信息列表的请求处理
	@RequestMapping("backend/ticketManage.html")
	public String totrainListPage(Model model,HttpSession session){
		
		Object userObj = session.getAttribute("user");
		if(userObj != null){
			List<Map> ticketList;
			try {
                ticketList = trainService.getTicketList();
				model.addAttribute("ticketList", ticketList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "backend/ticketManage";
		}else{
			return "redirect:/";
		}
	}

	@RequestMapping("orderTicket.html")
	@ResponseBody
	public String orderTicket(Model model,HttpSession session,@RequestParam String trainNo){
		String result = "failed";
		User user = (User) session.getAttribute("user");
		String username = user.getUsername();
		int train_no = Integer.valueOf(trainNo).intValue();
		Traininfo train = new Traininfo();
		try{
			train = trainService.getTraininfoDetail(train_no);
			if (train.getAva_ticket()>0){
				Map ticketOrderByUser = trainService.getTicketOrderByUser(username);
				if (null!=ticketOrderByUser) {
					ticketOrderByUser.put("userName",username);
					ticketOrderByUser.put("trainNo",train.getTrain_no());
					trainService.updateTicketOrder(ticketOrderByUser);
					trainService.updateTrainInfo(ticketOrderByUser);
				}else{
					ticketOrderByUser = new HashMap();
					ticketOrderByUser.put("userName",username);
					ticketOrderByUser.put("trainNo",train.getTrain_no());
					trainService.addTicketOrder(ticketOrderByUser);
					trainService.updateTrainInfo(ticketOrderByUser);
				}
				result = "success";

				Map orderResult = new HashMap();
				orderResult.put("trainNo",trainNo);
				orderResult.put("userName",username);
				orderResult.put("resultCode","0000");
				orderResult.put("resultMsg","订票成功");
				JSONObject object = JSONObject.fromObject(orderResult);
				amqpTemplate.convertAndSend("q.myQueue", object.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return result;
	}
}


