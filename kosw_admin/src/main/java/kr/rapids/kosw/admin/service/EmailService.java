package kr.rapids.kosw.admin.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 메일 발송 서비스
 * application.properties 의 메일 설정 변경 필수
 * 메일 발송을 텦플릿을 사용으로 freemaker 를 사용 (html 도 사용 가능) 
 * 
 * @author kangnamgyu
 *
 */
@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration freemarkerConfig;
	
	@Value("${spring.mail.username}")
	private String adminEmail;

	/**
	 * 템플릿을 사용하여 메일 발송
	 * @param templateName
	 * @param model
	 * @param title
	 * @param to
	 */
	public void sendMailUsinTemplate(String templateName, Map<String, Object> model ,String title ,String to){
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom(adminEmail);
				message.setTo(to);
				message.setSubject(title);
				try {
					Template t = freemarkerConfig.getTemplate(templateName);
					String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
					message.setText(html, true);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}


	@Async
	public void sendMail(String html,String title ,String to){
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom(adminEmail);
				message.setTo(to);
				message.setSubject(title);
				message.setText(html, true);
			}
		});
	}

	@Async
	public void sendMail(String html,String title , String from, String to){
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom(from);
				message.setTo(to);
				message.setSubject(title);
				message.setText(html, true);
			}
		});
	}



}
