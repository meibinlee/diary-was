package ac.hongik.tripdiary.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
	private static DateFormat formDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static DateFormat formDatestamp = new SimpleDateFormat("yyyyMMddhhmmss");
	private static DateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String toDateTime() {
		return formDateTime.format(new Date());
	}

	public static String toDateTime(Date date) {
		return formDateTime.format(date);
	}

	public static String toDate() {
		return formDate.format(new Date());
	}
	
	public static String toDate(Date date) {
		return formDate.format(date);
	}
	
	public static String toTimestamp() {
		return Long.toString(new Date().getTime());
	}

	public static String toTimestamp(Date date) {
		return Long.toString(date.getTime());
	}

	public static String toDatestamp() {
		return formDatestamp.format(new Date());
	}

}