package kr.rapids.kosw.admin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {

	public Util(){	}
	
	public static String checkNull(Object obj,String defaultVal){
        String strRtn = "" ;
        if(defaultVal==null) defaultVal = "" ;
        if(obj==null){
            strRtn = defaultVal ;
        }else{
            String tempStr = String.valueOf(obj).trim();
            if(tempStr.equals(""))
                strRtn = defaultVal ;
            else
                strRtn = tempStr ;
        }
        return strRtn ;
    }
	
	public static String getUuid(String flag){
 		//return isNull(flag,"").toLowerCase() + randomLowerString(4) + java.util.UUID.randomUUID().toString().replace("-", "");
 		return isNull(flag,"").toLowerCase() + format("yyMMddHHmmss") + java.util.UUID.randomUUID().toString().replace("-", "");
 	}
	
	public static String isNull(String arg,String NullVal){
 		return arg==null?NullVal:arg;
 	}
	
	public static String format(String pattern) {
    	SimpleDateFormat simpleDateFormat = (pattern == null) ? new SimpleDateFormat()
    		: new SimpleDateFormat(pattern);

    	return simpleDateFormat.format(new Date());
    }
	
	
	
	// 어드민 암호화
	private static BCryptPasswordEncoder cryptor = new BCryptPasswordEncoder();
	
	public static String createBcryptPwd(String pwd){
		return cryptor.encode(pwd);
	}
	
	public static boolean isMatchPassword(String rawPwd, String encryptPwd){
		return cryptor.matches(rawPwd, encryptPwd);
	}
}
