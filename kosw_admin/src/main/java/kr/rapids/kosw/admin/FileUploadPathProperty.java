package kr.rapids.kosw.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
	AppConfiguration 에서 @EnableConfigurationProperties 설정 하여 application.properties 를 읽도록 선언
 */
@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadPathProperty {

	private String logo;
	private String character;
	private String cafelogo;
	
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCafelogo() {
		return cafelogo;
	}
	public void setCafelogo(String cafelogo) {
		this.cafelogo = cafelogo;
	}
	
	
}
