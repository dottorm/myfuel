package it.olimpo.myfuel.utils;

import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtils {
		
	public static String getTitle(){
		
		Date date = new Date();
		
		String monthname=(String)android.text.format.DateFormat.format("MMMM yyyy", date.getTime());
				
		return monthname;
	}
	

}
