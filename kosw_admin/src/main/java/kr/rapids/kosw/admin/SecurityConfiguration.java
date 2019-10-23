package kr.rapids.kosw.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *  로그인 관련 설정
 *  https://docs.spring.io/spring-security/site/docs/5.0.4.RELEASE/reference/htmlsingle/#user-schema
 * @author kangnamgyu
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
//	@Autowired
//	DataSource dataSource;
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
//		jdbcDaoImpl.setDataSource(dataSource);
////		jdbcDaoImpl.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
////		jdbcDaoImpl.setAuthoritiesByUsernameQuery("SELECT username, authority FROM users WHERE username = ?");
//		jdbcDaoImpl.setUsersByUsernameQuery("SELECT email as username, passwd as password, active_flag = 'Y' as enabled FROM a_admin WHERE email = ?");
//		jdbcDaoImpl.setAuthoritiesByUsernameQuery("SELECT A.email AS username,U.auth_code AS authority FROM a_admin A JOIN a_admin_auth U ON A.admin_seq = U.admin_seq WHERE A.email = ?");
//
//		return jdbcDaoImpl;
//	}
//	@Autowired
//	UserDetailsService userDetailsService;
	
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(10);
//	}
//	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder); // Bcrypt 를 사용하여 비밀번호를 확인
//		return authProvider;
//	}
//	@Autowired
//	DaoAuthenticationProvider authProvider;
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authProvider);
//	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/static/**");
//	}
	
	@Autowired
	CustomAuthenticationProvider customAuthProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/**").permitAll() 
//		.and()
//			.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/favicon.ico").permitAll()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/assets/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/img/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/logoFile/**").permitAll()
			.antMatchers("/characterFile/**").permitAll()
			.antMatchers("/**").access("hasRole('ADMIN') OR hasRole('SUPER')") 
		.and()
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error")
			.usernameParameter("username").passwordParameter("password")
			.defaultSuccessUrl("/customerOne")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		.and()
			.csrf().disable();
	}
	
}
