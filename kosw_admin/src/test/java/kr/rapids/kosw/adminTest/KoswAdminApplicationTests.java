package kr.rapids.kosw.adminTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.rapids.kosw.admin.service.AdminService;
import kr.rapids.kosw.admin.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KoswAdminApplicationTests {

	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AdminService adminService;
	
	
	//@Test
	public void mailServiceTest() {
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Memorynotfound.com");
        model.put("location", "Belgium");
        model.put("signature", "http://memorynotfound.com");
		emailService.sendMailUsinTemplate("emailSample.ftl", model, "Free Maker Mail TEST", "dojo8888@gmail.com");
	}
	
	
	//@Test
	public void systemProperty(){
		Properties properties = System.getProperties();
		Iterator<Object> keyIter = properties.keySet().iterator();
		while(keyIter.hasNext()){
			Object key = keyIter.next();
			Object value = properties.get(key);
			System.out.println(key + " : " +value);
		}
		
		/**
			java.runtime.name : Java(TM) SE Runtime Environment
			sun.boot.library.path : /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib
			java.vm.version : 25.101-b13
			gopherProxySet : false
			java.vm.vendor : Oracle Corporation
			java.vendor.url : http://java.oracle.com/
			path.separator : :
			java.vm.name : Java HotSpot(TM) 64-Bit Server VM
			file.encoding.pkg : sun.io
			user.country : US
			sun.java.launcher : SUN_STANDARD
			sun.os.patch.level : unknown
			PID : 42420
			java.vm.specification.name : Java Virtual Machine Specification
			user.dir : /Users/kangnamgyu/JOB/STAIRWAY/SERVER/kingOfWalkwayAdmin
			java.runtime.version : 1.8.0_101-b13
			java.awt.graphicsenv : sun.awt.CGraphicsEnvironment
			java.endorsed.dirs : /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/endorsed
			os.arch : x86_64
			java.io.tmpdir : /var/folders/86/btcf7fl92_d73fk_dwglmx780000gn/T/
			line.separator : 
			
			java.vm.specification.vendor : Oracle Corporation
			os.name : Mac OS X
			sun.jnu.encoding : UTF-8
			spring.beaninfo.ignore : true
			java.library.path : /Users/kangnamgyu/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
			java.specification.name : Java Platform API Specification
			java.class.version : 52.0
			sun.management.compiler : HotSpot 64-Bit Tiered Compilers
			os.version : 10.12.6
			http.nonProxyHosts : local|*.local|169.254/16|*.169.254/16
			user.home : /Users/kangnamgyu
			user.timezone : Asia/Seoul
			java.awt.printerjob : sun.lwawt.macosx.CPrinterJob
			file.encoding : UTF-8
			java.specification.version : 1.8
			java.class.path : /Users/kangnamgyu/JOB/STAIRWAY/SERVER/kingOfWalkwayAdmin/target/test-classes:/Users/kangnamgyu/JOB/STAIRWAY/SERVER/kingOfWalkwayAdmin/target/classes:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-aop/2.0.1.RELEASE/spring-boot-starter-aop-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter/2.0.1.RELEASE/spring-boot-starter-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot/2.0.1.RELEASE/spring-boot-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/2.0.1.RELEASE/spring-boot-autoconfigure-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-logging/2.0.1.RELEASE/spring-boot-starter-logging-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/Users/kangnamgyu/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/Users/kangnamgyu/.m2/repository/org/apache/logging/log4j/log4j-to-slf4j/2.10.0/log4j-to-slf4j-2.10.0.jar:/Users/kangnamgyu/.m2/repository/org/apache/logging/log4j/log4j-api/2.10.0/log4j-api-2.10.0.jar:/Users/kangnamgyu/.m2/repository/org/slf4j/jul-to-slf4j/1.7.25/jul-to-slf4j-1.7.25.jar:/Users/kangnamgyu/.m2/repository/org/yaml/snakeyaml/1.19/snakeyaml-1.19.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-aop/5.0.5.RELEASE/spring-aop-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/aspectj/aspectjweaver/1.8.13/aspectjweaver-1.8.13.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-jdbc/2.0.1.RELEASE/spring-boot-starter-jdbc-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/com/zaxxer/HikariCP/2.7.8/HikariCP-2.7.8.jar:/Users/kangnamgyu/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-jdbc/5.0.5.RELEASE/spring-jdbc-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-tx/5.0.5.RELEASE/spring-tx-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-mail/2.0.1.RELEASE/spring-boot-starter-mail-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-context/5.0.5.RELEASE/spring-context-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-expression/5.0.5.RELEASE/spring-expression-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/com/sun/mail/javax.mail/1.6.1/javax.mail-1.6.1.jar:/Users/kangnamgyu/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-security/2.0.1.RELEASE/spring-boot-starter-security-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/security/spring-security-config/5.0.4.RELEASE/spring-security-config-5.0.4.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/security/spring-security-web/5.0.4.RELEASE/spring-security-web-5.0.4.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.0.1.RELEASE/spring-boot-starter-web-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-json/2.0.1.RELEASE/spring-boot-starter-json-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.9.5/jackson-datatype-jdk8-2.9.5.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.9.5/jackson-datatype-jsr310-2.9.5.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.9.5/jackson-module-parameter-names-2.9.5.jar:/Users/kangnamgyu/.m2/repository/org/hibernate/validator/hibernate-validator/6.0.9.Final/hibernate-validator-6.0.9.Final.jar:/Users/kangnamgyu/.m2/repository/javax/validation/validation-api/2.0.1.Final/validation-api-2.0.1.Final.jar:/Users/kangnamgyu/.m2/repository/org/jboss/logging/jboss-logging/3.3.2.Final/jboss-logging-3.3.2.Final.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/classmate/1.3.4/classmate-1.3.4.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-web/5.0.5.RELEASE/spring-web-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-webmvc/5.0.5.RELEASE/spring-webmvc-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/mybatis/spring/boot/mybatis-spring-boot-starter/1.3.2/mybatis-spring-boot-starter-1.3.2.jar:/Users/kangnamgyu/.m2/repository/org/mybatis/spring/boot/mybatis-spring-boot-autoconfigure/1.3.2/mybatis-spring-boot-autoconfigure-1.3.2.jar:/Users/kangnamgyu/.m2/repository/org/mybatis/mybatis/3.4.6/mybatis-3.4.6.jar:/Users/kangnamgyu/.m2/repository/org/mybatis/mybatis-spring/1.3.2/mybatis-spring-1.3.2.jar:/Users/kangnamgyu/.m2/repository/mysql/mysql-connector-java/5.1.46/mysql-connector-java-5.1.46.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-tomcat/2.0.1.RELEASE/spring-boot-starter-tomcat-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/javax/annotation/javax.annotation-api/1.3.2/javax.annotation-api-1.3.2.jar:/Users/kangnamgyu/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/8.5.29/tomcat-embed-core-8.5.29.jar:/Users/kangnamgyu/.m2/repository/org/apache/tomcat/embed/tomcat-embed-el/8.5.29/tomcat-embed-el-8.5.29.jar:/Users/kangnamgyu/.m2/repository/org/apache/tomcat/embed/tomcat-embed-websocket/8.5.29/tomcat-embed-websocket-8.5.29.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-starter-test/2.0.1.RELEASE/spring-boot-starter-test-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-test/2.0.1.RELEASE/spring-boot-test-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/boot/spring-boot-test-autoconfigure/2.0.1.RELEASE/spring-boot-test-autoconfigure-2.0.1.RELEASE.jar:/Users/kangnamgyu/.m2/repository/com/jayway/jsonpath/json-path/2.4.0/json-path-2.4.0.jar:/Users/kangnamgyu/.m2/repository/net/minidev/json-smart/2.3/json-smart-2.3.jar:/Users/kangnamgyu/.m2/repository/net/minidev/accessors-smart/1.2/accessors-smart-1.2.jar:/Users/kangnamgyu/.m2/repository/org/ow2/asm/asm/5.0.4/asm-5.0.4.jar:/Users/kangnamgyu/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/kangnamgyu/.m2/repository/org/assertj/assertj-core/3.9.1/assertj-core-3.9.1.jar:/Users/kangnamgyu/.m2/repository/org/mockito/mockito-core/2.15.0/mockito-core-2.15.0.jar:/Users/kangnamgyu/.m2/repository/net/bytebuddy/byte-buddy/1.7.11/byte-buddy-1.7.11.jar:/Users/kangnamgyu/.m2/repository/net/bytebuddy/byte-buddy-agent/1.7.11/byte-buddy-agent-1.7.11.jar:/Users/kangnamgyu/.m2/repository/org/objenesis/objenesis/2.6/objenesis-2.6.jar:/Users/kangnamgyu/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/kangnamgyu/.m2/repository/org/hamcrest/hamcrest-library/1.3/hamcrest-library-1.3.jar:/Users/kangnamgyu/.m2/repository/org/skyscreamer/jsonassert/1.5.0/jsonassert-1.5.0.jar:/Users/kangnamgyu/.m2/repository/com/vaadin/external/google/android-json/0.0.20131108.vaadin1/android-json-0.0.20131108.vaadin1.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-core/5.0.5.RELEASE/spring-core-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-jcl/5.0.5.RELEASE/spring-jcl-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-test/5.0.5.RELEASE/spring-test-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/xmlunit/xmlunit-core/2.5.1/xmlunit-core-2.5.1.jar:/Users/kangnamgyu/.m2/repository/org/springframework/security/spring-security-test/5.0.4.RELEASE/spring-security-test-5.0.4.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/security/spring-security-core/5.0.4.RELEASE/spring-security-core-5.0.4.RELEASE.jar:/Users/kangnamgyu/.m2/repository/javax/servlet/jstl/1.2/jstl-1.2.jar:/Users/kangnamgyu/.m2/repository/de/bytefish/fcmjava/fcmjava-client/2.5/fcmjava-client-2.5.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.9.0/jackson-annotations-2.9.0.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.9.5/jackson-databind-2.9.5.jar:/Users/kangnamgyu/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.9.5/jackson-core-2.9.5.jar:/Users/kangnamgyu/.m2/repository/org/apache/httpcomponents/httpclient/4.5.5/httpclient-4.5.5.jar:/Users/kangnamgyu/.m2/repository/org/apache/httpcomponents/httpcore/4.4.9/httpcore-4.4.9.jar:/Users/kangnamgyu/.m2/repository/commons-codec/commons-codec/1.11/commons-codec-1.11.jar:/Users/kangnamgyu/.m2/repository/de/bytefish/fcmjava/fcmjava-core/2.5/fcmjava-core-2.5.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-context-support/5.0.5.RELEASE/spring-context-support-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/springframework/spring-beans/5.0.5.RELEASE/spring-beans-5.0.5.RELEASE.jar:/Users/kangnamgyu/.m2/repository/org/freemarker/freemarker/2.3.28/freemarker-2.3.28.jar:/Users/kangnamgyu/spring-tool-suite-3.8.4.RELEASE-e4.6.3-macosx-cocoa-x86_64/sts-bundle/STS.app/Contents/Eclipse/configuration/org.eclipse.osgi/298/0/.cp/:/Users/kangnamgyu/spring-tool-suite-3.8.4.RELEASE-e4.6.3-macosx-cocoa-x86_64/sts-bundle/STS.app/Contents/Eclipse/configuration/org.eclipse.osgi/297/0/.cp/
			user.name : kangnamgyu
			java.vm.specification.version : 1.8
			sun.java.command : org.eclipse.jdt.internal.junit.runner.RemoteTestRunner -version 3 -port 63248 -testLoaderClass org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader -loaderpluginname org.eclipse.jdt.junit4.runtime -classNames kr.rapids.app.kingOfWalkwayAdmin.KingOfWalkwayAdminApplicationTests
			java.home : /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre
			sun.arch.data.model : 64
			user.language : en
			java.specification.vendor : Oracle Corporation
			awt.toolkit : sun.lwawt.macosx.LWCToolkit
			java.vm.info : mixed mode
			java.version : 1.8.0_101
			java.ext.dirs : /Users/kangnamgyu/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java
			sun.boot.class.path : /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/sunrsasign.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home/jre/classes
			java.awt.headless : true
			java.vendor : Oracle Corporation
			file.separator : /
			java.vendor.url.bug : http://bugreport.sun.com/bugreport/
			sun.io.unicode.encoding : UnicodeBig
			sun.cpu.endian : little
			socksNonProxyHosts : local|*.local|169.254/16|*.169.254/16
			ftp.nonProxyHosts : local|*.local|169.254/16|*.169.254/16
			sun.cpu.isalist : 
		 */
		
	}
	


}
