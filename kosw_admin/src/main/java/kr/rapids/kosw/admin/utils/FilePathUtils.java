package kr.rapids.kosw.admin.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.rapids.kosw.admin.FileUploadPathProperty;


@Component
public class FilePathUtils {

	@Autowired
	private FileUploadPathProperty fileUploadPathProperty;
	
	private String catalinaHome;
	
	private String fileUploadPathOfLogo;
	private String fileUploadPathOfCharacter;
	private String fileUploadPathOfCafeLogo;
	
	public FilePathUtils() {
		Properties properties = System.getProperties();
		String object = (String)properties.get("catalina.base");
		this.catalinaHome = object;
	}
	
	/**
	 * SO, CAT NOT USE AUTOWIRED ON CONSTRUCTION.
	 */
	@PostConstruct
	void setPaths(){
		this.fileUploadPathOfLogo = Paths.get(this.catalinaHome, fileUploadPathProperty.getLogo()).toString();
		File directoryLogo = new File(this.fileUploadPathOfLogo);
		if (!directoryLogo.exists()){
			directoryLogo.mkdirs();
		}
		
		this.fileUploadPathOfCharacter = Paths.get(this.catalinaHome, fileUploadPathProperty.getCharacter()).toString();
		File directoryCharacter = new File(this.fileUploadPathOfCharacter);
		
		if (!directoryCharacter.exists()){
			directoryCharacter.mkdirs();
		}
		
		this.fileUploadPathOfCafeLogo = Paths.get(this.catalinaHome, fileUploadPathProperty.getCafelogo()).toString();
		File directoryCafeLogo = new File(this.fileUploadPathOfCafeLogo);
		if (!directoryCafeLogo.exists()){
			directoryCafeLogo.mkdirs();
		}
		
	}
	
	
	public File getLogoFile(){
		File file = new File(this.fileUploadPathOfLogo);
		return file;
	}
	
	public File getCharacterFile(){
		File file = new File(this.fileUploadPathOfCharacter);
		return file;
	}
	
	public File getCafeLogoFile(){
		File file = new File(this.fileUploadPathOfCafeLogo);
		return file;
	}
	
	
	
	
	
	public String getFileUploadPathOfLogo() {
		return fileUploadPathOfLogo;
	}

	public void setFileUploadPathOfLogo(String fileUploadPathOfLogo) {
		this.fileUploadPathOfLogo = fileUploadPathOfLogo;
	}
	
	public String getFileUploadPathOfCafeLogo() {
		return fileUploadPathOfCafeLogo;
	}
	
	public void setFileUploadPathOfCafeLogo(String fileUploadPathOfCafeLogo) {
		this.fileUploadPathOfCafeLogo = fileUploadPathOfCafeLogo;
	}

	public String getCatalinaHome() {
		return catalinaHome;
	}

	public void setCatalinaHome(String catalinaHome) {
		this.catalinaHome = catalinaHome;
	}

	public FileUploadPathProperty getFileUploadPathProperty() {
		return fileUploadPathProperty;
	}

	public void setFileUploadPathProperty(FileUploadPathProperty fileUploadPathProperty) {
		this.fileUploadPathProperty = fileUploadPathProperty;
	}


	public String getFileUploadPathOfCharacter() {
		return fileUploadPathOfCharacter;
	}

	public void setFileUploadPathOfCharacter(String fileUploadPathOfCharacter) {
		this.fileUploadPathOfCharacter = fileUploadPathOfCharacter;
	}
	
	
	
}
