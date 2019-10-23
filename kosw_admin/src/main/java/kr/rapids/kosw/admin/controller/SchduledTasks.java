package kr.rapids.kosw.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.rapids.common.util.RapidsMap;
import kr.rapids.kosw.admin.model.Push;
import kr.rapids.kosw.admin.service.AdminService;
import kr.rapids.kosw.admin.service.FcmService;
import kr.rapids.kosw.admin.utils.PushType;

@Component
public class SchduledTasks {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FcmService fcmService;
	
	private final static Logger logger = LoggerFactory.getLogger(SchduledTasks.class);
	
	/**
	 * 30 분마다 체크
	 * 8~23 시 사이 매 0,30분 시 채크
	 *     field         allowed values
           -----         --------------
           second        0-59
           minute        0-59
           hour          0-23
           day of month  1-31
           month         1-12 (or names, see below)
           day of week   0-7 (0 or 7 is Sun, or use names)
	 */
	@Scheduled(cron="0 0/30 8-22 * * *")
//	@Scheduled(cron="0 * * * * *")
	public void scheduledPushSend(){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    String now = dateFormat.format(new Date());
	    logger.info("SCHEDULED PUSH SEND : {}", now);
	    
	    List<Push> pushList =  adminService.scheduledPushList();
	    
	    HashMap<String, String> data = new RapidsMap<>();
	    data.put("push_type", PushType.GENERAL.name());
		
	    if (pushList != null && pushList.size() > 0){
	    	for (Push push : pushList) {
		    	String title = push.getPushTitle();
		    	String body = push.getPushContent();
		    	List<String> tokens = adminService.pushTargetTokens(push);
		    	if (tokens != null && tokens.size() > 0) {
		    		fcmService.sendFcmToGroup(title, body, tokens, data);  // 푸쉬 메세지 발송
		    	}
		    	adminService.pushSent(push); // 발송 여부 저장
			}
	    }else{
	    	logger.info("NOTHING TO SEND PUSH");
	    }
	    
	    
	}
}
