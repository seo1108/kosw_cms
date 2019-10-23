package kr.rapids.kosw.admin;

import de.bytefish.fcmjava.http.options.IFcmClientSettings;

/**
 * Firebase 기본 설정 클래스 
 * 수정할 필요 없음
 * @author kangnamgyu
 *
 */
public class FcmSetting implements IFcmClientSettings{

	private String apiKey;
	private String url;

	@Override
	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getFcmUrl() {
		return this.url;
	}

}
