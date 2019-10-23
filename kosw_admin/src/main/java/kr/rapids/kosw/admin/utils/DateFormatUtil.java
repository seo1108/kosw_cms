package kr.rapids.kosw.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
	
	public static String fromFormat2Format(String dateStr, String fromFormat, String toFormat){
		if (dateStr == null || "".equals(dateStr)){
			return null;
		}
		
		SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
		SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat);

		try {
			Date date = fromDateFormat.parse(dateStr);
			String result = toDateFormat.format(date);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

        return today;
	}
}
