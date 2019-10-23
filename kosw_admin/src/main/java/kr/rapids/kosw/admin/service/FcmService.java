package kr.rapids.kosw.admin.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import de.bytefish.fcmjava.client.FcmClient;
import de.bytefish.fcmjava.model.enums.ErrorCodeEnum;
import de.bytefish.fcmjava.model.options.FcmMessageOptions;
import de.bytefish.fcmjava.requests.data.DataMulticastMessage;
import de.bytefish.fcmjava.requests.data.DataUnicastMessage;
import de.bytefish.fcmjava.requests.notification.NotificationPayload;
import de.bytefish.fcmjava.responses.FcmMessageResponse;
import de.bytefish.fcmjava.responses.FcmMessageResultItem;
import kr.rapids.kosw.admin.controller.AdminController;

/**
 * 앱 푸쉬 메세지 발송 서비스
 * FcmConfiguration 확인
 * @author kangnamgyu
 *
 */
@Service
public class FcmService {

	@Autowired
	private FcmClient fcmClient;
	
	private final static Logger logger = LoggerFactory.getLogger(FcmService.class);

	private static final int TOKEN_SUB_SIZE = 500;
	
	
	/**
	 * 단일 대상 FCM 전송
	 * @param title
	 * @param body
	 * @param token
	 * @param data
	 * @return
	 */
	public boolean sendFcmToSingle(String title, String body, String token, Map<String, String> data){
		FcmMessageOptions options = FcmMessageOptions.builder()
		.setTimeToLive(Duration.ofHours(1))
		.setContentAvailable(true)  // iOS BACKGROUND FETCH ENABLE
		.build();
		
		NotificationPayload payload = NotificationPayload.builder()
		.setTitle(title)
		.setBody(body)
		.setSound("default")
		.build();
		
		DataUnicastMessage msg = new DataUnicastMessage(options, token, data, payload);
		
		FcmMessageResponse sendResult = fcmClient.send(msg);
		List<FcmMessageResultItem> results = sendResult.getResults();
		for (FcmMessageResultItem r : results){
			ErrorCodeEnum errorCode = r.getErrorCode();
			if (errorCode != null){
				System.out.println("error send fcm message : " + errorCode );
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 푸쉬 메세지 단체 대상 
	 * @param title
	 * @param body
	 * @param to
	 * @param data
	 * @return
	 */
	public boolean sendFcmToGroup(String title, String body, List<String> tokens, HashMap<String, String> data){
		if (tokens == null || tokens.size() < 1){
			return false;
		}
		FcmMessageOptions options = FcmMessageOptions.builder()
		.setTimeToLive(Duration.ofHours(1))
		.setContentAvailable(true)  // iOS BACKGROUND FETCH ENABLE
		.build();
		
		NotificationPayload notification = NotificationPayload.builder()
		.setTitle(title)
		.setSound("default")
		.setBody(body)
		.build();
		
		while (tokens.size() > TOKEN_SUB_SIZE){
			List<String> sub = tokens.subList(0, TOKEN_SUB_SIZE);
			this.send(options, sub, data, notification);
			tokens = tokens.subList(TOKEN_SUB_SIZE, tokens.size());
		}
		this.send(options, tokens, data, notification);
		return true;
	}
	
	@Async
	public void send(FcmMessageOptions options, List<String> tokens, Object data, NotificationPayload notification){
		DataMulticastMessage dataMulticastMessage = new DataMulticastMessage(options, tokens, data, notification);
		FcmMessageResponse sendResult = fcmClient.send(dataMulticastMessage);
		if (sendResult.getNumberOfFailure() > 0){
			logger.error("SUCESS : {} / FAILURE : {}",sendResult.getNumberOfSuccess(),  sendResult.getNumberOfFailure());
		}
		
		List<FcmMessageResultItem> results = sendResult.getResults();
		for (FcmMessageResultItem r : results){
			ErrorCodeEnum errorCode = r.getErrorCode();
			if (errorCode != null){
				logger.error("error send fcm message : {}", errorCode.name() );
			}
		}
	}
	
	
	
}
