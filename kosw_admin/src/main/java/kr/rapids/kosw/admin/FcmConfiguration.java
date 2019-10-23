package kr.rapids.kosw.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.bytefish.fcmjava.client.FcmClient;

/**
 * Firebase 설정 - application.property 의 firebase.key 와 firebase.url 을 설정해야한다.
 * @author kangnamgyu
 */
@Configuration
public class FcmConfiguration {

	
	@Value("${firebase.key}")
    private String FCM_KEY;
	
	@Value("${firebase.url}")
    private String FCM_URL;
	
    @Bean
    public FcmClient fcmClient(){
    	FcmSetting settings =  new FcmSetting();
		settings.setApiKey(FCM_KEY);
		settings.setUrl(FCM_URL);
		return new FcmClient(settings);
    }
    
    
}


