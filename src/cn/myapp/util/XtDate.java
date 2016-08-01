package cn.myapp.util;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat; 

public class XtDate {

	public static String mDateStringFormat = "yyyy-MM-dd" ;
    public static String mTimeStringFormat = "HH:mm:ss" ;
    public static String mDateTimeStringFormat = "yyyy-MM-dd HH:mm:ss" ;
	
	public static Date getCurrentDayOriginalDate() {
		Calendar calendar = Calendar.getInstance() ;
		calendar.set(Calendar.HOUR_OF_DAY, 0); 
		calendar.set(Calendar.MINUTE, 0); 
		calendar.set(Calendar.SECOND, 0); 
		Date today = calendar.getTime();
		return today ;
	}
	
	public static Date getNowDate() {
		return new java.util.Date() ;  
	}
    
	public static Date getDateWithString(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(mDateTimeStringFormat) ;
		java.util.Date date = sdf.parse(dateStr);   
		return date ;
	}
	
	public static String getStringWithDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(mDateTimeStringFormat) ;  
		return sdf.format(date) ;  
	}
	
}
