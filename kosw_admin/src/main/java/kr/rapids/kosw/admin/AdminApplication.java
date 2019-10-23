package kr.rapids.kosw.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 어플리케이션 시작
 * 스케줄 활성화
 * @author kangnamgyu
 *
 */
@SpringBootApplication
@EnableScheduling
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}




