package kr.rapids.kosw.admin.utils;

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
}
