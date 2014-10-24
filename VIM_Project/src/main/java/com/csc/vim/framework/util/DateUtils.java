package com.csc.vim.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

	/**
	 * Get Current Time in TimeStamp Format
	 * @author kkhamliche
	 * @version 2.0		21/08/2014				
	 * @return Current TimeStamp
	 */
	public Timestamp getCurrentTimeStamp() {
		java.util.Date date= new java.util.Date();
		return new Timestamp(date.getTime());
	}


//	public Timestamp stringToTimestamp(String pDate, String pFormat) {
//		if ((pDate == null) || (pDate.equals(""))) {
//			return null;
//		}
//		else {
//			return new Timestamp(stringToDate(pDate, pFormat).getTime());
//		}
//	}
	/**
	 * Convert a String date to date Object
	 * @param p_date	date to be transform
	 * @param p_format	the format input=output of the date
	 * @return Date		The date object with the right format
	 */
	public Date stringToDateSAP(String sDate, String sFormat) {
		Date dateForm  = null;

		if (sDate.equals("")) {
			return null;
		} else {
			if (!"nulldate".equalsIgnoreCase(sDate)) {
				sDate = sDate.substring(0, 10).replace("/", ".");
				//TODO Gerer l'exception
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
					dateForm = formatter.parse(sDate);
				} catch (ParseException e) {
					e.printStackTrace();

				}
			}
		}

		return dateForm;
	}
	
	public static String simpleDateToString(Date p_date, String p_formatOutput) {
		String result = "";
		if (p_date != null) {
			SimpleDateFormat dateOutput = new SimpleDateFormat(p_formatOutput);
			result = dateOutput.format(p_date);
		}
		return result;
		
	}
	/**
	 * Convert a String date to date Object
	 * @param p_date	date to be transform
	 * @param p_format	the format input=output of the date
	 * @return Date		The date object with the right format
	 */
	public Date stringToDateDCTM(String sDate, String sFormat) {
		
		Date dateForm  = null;

		if (sDate.equals("")) {
			return null;
		} else {
			if (!"nulldate".equalsIgnoreCase(sDate)) {
				sDate = sDate.substring(0, 10);
				try {
					 dateForm = new SimpleDateFormat(sFormat).parse(sDate);
					 
				} catch (ParseException e) {
					e.printStackTrace();

				}
			}
		}

		return dateForm;
	}

	/**
	 * Convert a String date to date Object
	 * @param p_date	date to be transform
	 * @param p_inputFormat	the input format of the date
	 * @param p_outputFormat the output format of the date
	 * @return Date		The date object with the right format
	 */
//	public Date stringToDate(String sDate, String p_inputFormat, String p_outputFormat) {
//		Date dateForm = null;
//
//		if (sDate.equals("")) {
//			return null;
//		} else {
//			if (!"nulldate".equalsIgnoreCase(sDate)) {
//
//				SimpleDateFormat inputDate = new SimpleDateFormat(p_inputFormat);
//				SimpleDateFormat output = new SimpleDateFormat(p_outputFormat);
//
//				try {
//
//					inputDate.parse(sDate);
//
//					dateForm = stringToDate(output.format(inputDate), p_outputFormat);
//
//				} catch (ParseException e1) {
//					e1.printStackTrace();
//				}
//
//			}
//		}
//
//		return dateForm;
//	}

	public String dateToString(Date p_date, String p_formatOutput) {
		String result = "";
		if (p_date != null) {
			SimpleDateFormat dateOutput = new SimpleDateFormat(p_formatOutput);
			result = dateOutput.format(p_date);
		}
		return result;

	}

	public  String changeDateFormat(Date p_date, String p_outputFormat) {
		if (p_date != null) {
			SimpleDateFormat dateOutput = new SimpleDateFormat(p_outputFormat);
			return dateOutput.format(p_date);
		} else {
			return "";
		}

	}

	public String now(String p_format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		return sdf.format(cal.getTime());
	}

//	public Date nowDate(String p_format) {
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
//		return stringToDate(sdf.format(cal.getTime()), p_format);
//	}

	public int getYear(Date p_date) {

		Calendar cal=Calendar.getInstance();
		cal.setTime(p_date);

		//Modif SDN return cal.YEAR;
		return cal.get(Calendar.YEAR);
	}

	public String getYearStr(Date p_date, String p_format) {
		int result = getYear(p_date);
		String resultStr = String.valueOf(result);

		if (p_format.equals("yyyy")) {
			if (resultStr.length() <4 ) {
				for (int i=resultStr.length(); i<4; i++) {
					resultStr = "0" + resultStr;
				}
			}
		}

		return resultStr;
	}

	public int getMonth(Date p_date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(p_date);

		// Bug; have to add +1
		return cal.get(Calendar.MONTH) + 1;
	}

	public String getMonthStr(Date p_date, String p_format) {
		int result = getMonth(p_date);
		String resultStr = String.valueOf(result);

		if (p_format.equals("MM")) {
			if (resultStr.length() <2 ) {
				resultStr = "0" + resultStr;
			}
		}

		return resultStr;
	}

	public int getDay(Date p_date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(p_date);

		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public String getDayStr(Date p_date, String p_format) {
		int result = getDay(p_date);
		String resultStr = String.valueOf(result);

		if (p_format.equals("dd")) {
			if (resultStr.length() <2 ) {
				resultStr = "0" + resultStr;
			}
		}

		return resultStr;
	}

	public Date todayAdd(String p_unit, int p_value) {
		Calendar cal = Calendar.getInstance();
		if (p_unit.equalsIgnoreCase("year")) {
			cal.add(Calendar.YEAR, p_value);
		} else if (p_unit.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, p_value);
		}
		//else if (p_unit.equalsIgnoreCase("day"))
		//cal.add(Calendar.D, p_value);
		return cal.getTime();
	}

	public Date addDate(Date p_date, String p_unit, int p_value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(p_date);
		if (p_unit.equalsIgnoreCase("year")) {
			cal.add(Calendar.YEAR, p_value);
		} else if (p_unit.equalsIgnoreCase("month")) {
			cal.add(Calendar.MONTH, p_value);
		} else if (p_unit.equalsIgnoreCase("day")) {
			cal.add(Calendar.DAY_OF_WEEK, p_value);
		}

		return cal.getTime();
	}

	public boolean isCurrentMonth(Date p_date) {
		String[] today = now("dd/MM/yyyy").split("/");
		String[] current = dateToString(p_date, "dd/MM/yyyy").split("/");

		if (today[1].equals(current[1])) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCurrentYear(Date p_date) {
		String[] today = now("dd/MM/yyyy").split("/");
		String[] current = dateToString(p_date, "dd/MM/yyyy").split("/");

		if (today[2].equals(current[2])) {
			return true;
		} else {
			return false;
		}
	}
}
