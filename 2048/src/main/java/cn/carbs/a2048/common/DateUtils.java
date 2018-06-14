package cn.carbs.a2048.common;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String HHMMSS = "HHmmss";
    public static final String MM_DD = "MM-dd";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_S_file_name = "yyyy_MM_dd_HH_mm_ss_S";
	public static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";
	public static final String WESTER_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String MM_DD_HH_MM_SS = "MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static SimpleDateFormat mDateFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.getDefault());

    public static final SimpleDateFormat sdf_MM_DD =
                        new SimpleDateFormat(MM_DD);
    public static final SimpleDateFormat sdf_YYYY_MM_DD =
                        new SimpleDateFormat(YYYY_MM_DD);
    public static final SimpleDateFormat sdf_YYYY_MM_DD_HH_MM_SS =
                        new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
    public static final SimpleDateFormat sdf_YYYY_MM_DD_HH_MM_SS_S_file_name =
                        new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_S_file_name);
    public static final SimpleDateFormat sdf_YYYY_MM_DD_HH_MM_SS_S =
                        new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_S);
    public static final SimpleDateFormat sdf_WESTER_YYYY_MM_DD_HH_MM_SS =
                        new SimpleDateFormat(WESTER_YYYY_MM_DD_HH_MM_SS);
    public static final SimpleDateFormat sdf_MM_DD_HH_MM_SS =
                        new SimpleDateFormat(MM_DD_HH_MM_SS);
    public static final SimpleDateFormat sdf_YYYY_MM_DD_HH_MM =
            new SimpleDateFormat(YYYY_MM_DD_HH_MM);

    public static final SimpleDateFormat sdf_YYYYMMDDHHMM =
            new SimpleDateFormat(YYYYMMDDHHMM);

    public static final SimpleDateFormat sdf_YYYYMMDD =
            new SimpleDateFormat(YYYYMMDD);

	public DateUtils() {

	}


    /**
     * 获取当前时间用于填充本地数据库的createTime字段
     *
     * @return
     */
    public static String getCurrentDateYYYYMMDD() {
        return dateTime2String(Calendar.getInstance(Locale.getDefault()).getTime(), YYYYMMDD);
    }

    public static String getDateYYYYMMDD(Date date) {
        return dateTime2String(date, YYYYMMDD);
    }

    public static String getDateBeforeYYYYMMDD(long secondBefore) {
        return dateTime2String(new Date(Calendar.getInstance(Locale.getDefault()).getTime().getTime() - secondBefore), YYYYMMDD);
    }

    /**
     * 获取当前时间用于填充本地数据库的createTime字段
     *
     * @return
     */
    public static String getCurrentDateHHMMSS() {
        return dateTime2String(Calendar.getInstance(Locale.getDefault()).getTime(), HHMMSS);
    }

    public static String getDateHHMMSS(Date date) {
        return dateTime2String(date, HHMMSS);
    }


	/**
	 * 获取当前时间用于填充本地数据库的createTime字段
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return dateTime2String(Calendar.getInstance(Locale.getDefault()).getTime());
	}

	/**
	 * 获取当前时间用于填充本地数据库的createTime字段
	 * 
	 * @return
	 */
	public static Date getCurrentTimeDate() {
		return stringToDateTime(dateTime2String(Calendar.getInstance(Locale.getDefault()).getTime()));
	}



	/**
	 * 将日期格式化
	 * 
	 * @param date
	 * @param formater
	 * @return
	 */
	public static Date formatDate(Date date, String formater) {
		if (null == date) {
			return null;
		}
		if (TextUtils.isEmpty(formater)) {
			formater = YYYY_MM_DD;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formater);

		return stringToDateTime(sdf.format(date));
	}

    /**
     * 将日期转化为字符串
     *
     * @param date
     * @return
     */
    public static String dateTime2String(Date date) {
        if (null == date) {
            return "";
        }
        return sdf_YYYY_MM_DD_HH_MM_SS.format(date);
    }

	public static String getTimeSpanByDateAndTime(String date, String time) {
		if (date == null || time == null) {
			return "";
		}

		Date now = new Date();
		Date des = null;

		if (time.length() == 6) {
			des = DateUtils.stringToDateTime(date + time.substring(0, 4), DateUtils.sdf_YYYYMMDDHHMM);
		} else if (time.length() == 4) {
			des = DateUtils.stringToDateTime(date + time, DateUtils.sdf_YYYYMMDDHHMM);
		} else {
			return "";
		}
		long timeSpan = des.getTime() - now.getTime();
		return getTimeHintByMilliSpan(timeSpan);
	}

	public static String getTimeHintByMilliSpan(long timeSpan) {
		long days = timeSpan / 86400000;
		if (days == 0) {
			return "今天";
		}
		return String.valueOf(Math.abs(days)) + "天" + (timeSpan > 0 ? "后" : "前");
	}


	public static String dateTime2StringOfMonthAndDay(Date date) {
		if (null == date) {
			return "";
		}
		return sdf_MM_DD.format(date);
	}

	/**
	 * 将日期转化为字符串
	 * 
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String dateTime2String(Date date, String formater) {
		if (null == date) {
			return "";
		}

        if(formater == null || formater.length() == 0){
            return sdf_YYYY_MM_DD_HH_MM_SS.format(date);
        }

        if(YYYY_MM_DD.equals(formater)){
            return sdf_YYYY_MM_DD.format(date);
        }else if(MM_DD.equals(formater)) {
            return sdf_MM_DD.format(date);
        }else if (YYYY_MM_DD_HH_MM_SS.equals(formater)){
            return sdf_YYYY_MM_DD_HH_MM_SS.format(date);
        }else if (YYYY_MM_DD_HH_MM_SS_S.equals(formater)){
            return sdf_YYYY_MM_DD_HH_MM_SS_S.format(date);
        }else if (WESTER_YYYY_MM_DD_HH_MM_SS.equals(formater)){
            return sdf_WESTER_YYYY_MM_DD_HH_MM_SS.format(date);
        }else if(YYYY_MM_DD_HH_MM_SS_S_file_name.equals(formater)){
            return sdf_YYYY_MM_DD_HH_MM_SS_S_file_name.format(date);
        }else if(MM_DD_HH_MM_SS.equals(formater)){
            return sdf_MM_DD_HH_MM_SS.format(date);
        }else if(YYYY_MM_DD_HH_MM.equals(formater)){
            return sdf_YYYY_MM_DD_HH_MM.format(date);
        }

		return new SimpleDateFormat(formater).format(date);
	}

    public static Date getDateByDateTime(String dateStr, String timeStr){

        if (!TextUtils.isEmpty(dateStr) && dateStr.length() == 8){
            Date date = stringToDateTime(dateStr, sdf_YYYYMMDD);
            if (!TextUtils.isEmpty(timeStr) && timeStr.length() == 6) {
                date.setHours(Integer.valueOf(timeStr.substring(0, 2)));
                date.setMinutes(Integer.valueOf(timeStr.substring(2, 4)));
                date.setSeconds(Integer.valueOf(timeStr.substring(4, 6)));
            }
            return date;
        }
        return new Date();
    }

    public static Date stringToDateTime(String s, SimpleDateFormat f) {
        try {
            return f.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
	/**
	 * 将字符串转化为日期
	 * 
	 * @param s
	 * @return
	 */
	public static Date stringToDateTime(String s) {
		if (null == s || "".equals(s)) {
			return new Date();
		}
		SimpleDateFormat sdf;
		if (s.matches("\\d{4}-\\d{2}-\\d{2}")) {
			sdf = new SimpleDateFormat(YYYY_MM_DD);
		} else if (s.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
			sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		} else if (s.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
			sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_S);
		} else if (s.matches("\\d{4}/\\d{1,2}/\\d{1,2} \\d{2}:\\d{2}:\\d{2}")) {
			sdf = new SimpleDateFormat(WESTER_YYYY_MM_DD_HH_MM_SS);
		} else if (s.matches("\\d{1,13}")) {//146770153853
            return new Date(Long.parseLong(s));
        }else {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * string转化为calendar
	 * 
	 * @param str
	 * @return
	 */
	public static Calendar parseStrToCld(String str) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stringToDateTime(str));
		return calendar;
	}

	/**
	 * calendar 转化为字符串
	 * 
	 * @param cal
	 * @return
	 */
	public static String getFormatCld(Calendar cal) {
		return dateTime2String(cal.getTime());
	}

	/*
	 * 根据给定的long型时间获得时间字符串
	 */
	public static String getDateStrByLong(long timeInMillis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMillis);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String result = sdf.format(cal.getTime());
		return result;
	}

	/**
	 * 获取两个日期相差月数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2)
            throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result;
    }

	/**
	 * 获取两个日期相差月数
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(Date date1, Date date2){
        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result;
    }
	
	/**
	 * 获取两个日期相差天数
	 * @param early
	 * @param late
	 * @return
	 * @throws ParseException
	 */

	public static final long daysBetween(Date early, Date late) {
	     
        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        calst.setTime(early);
         caled.setTime(late);
         //设置时间为0时
         calst.set(Calendar.HOUR_OF_DAY, 0);
         calst.set(Calendar.MINUTE, 0);
         calst.set(Calendar.SECOND, 0);
         caled.set(Calendar.HOUR_OF_DAY, 0);
         caled.set(Calendar.MINUTE, 0);
         caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数   
         long days = ((caled.getTime().getTime() / 1000) -  (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
        return days;   
   }   
  
	/**
	 * 获得某天某点
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTimeDate(Date date, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(date.getYear() + 1900, date.getMonth(), date.getDate(), hour, minute, second);
		return c.getTime();
	}

	public static String getCompareDate(int field) {
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		switch (field) {
		case Calendar.DAY_OF_MONTH:
		case Calendar.WEEK_OF_MONTH:
		case Calendar.MONTH:
			calendar.add(field, -1);
			break;
		default:
			break;
		}
		return mDateFormat.format(calendar.getTime());
	}

	/**
	 * 两个日期字符串的比较
	 * 
	 * @param time1
	 * @param time2
	 * @return 0 if the times of the two Calendars are equal, -1 if the time of
	 *         this Calendar is before the other one, 1 if the time of this
	 *         Calendar is after the other one.
	 */
	public static int timeCompare(String time1, String time2) {
		DateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		Calendar c1 = Calendar.getInstance(Locale.getDefault());
		Calendar c2 = Calendar.getInstance(Locale.getDefault());
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return c1.compareTo(c2);
	}

	/**
	 * 比较日期（年月日） 0:相等 -1:小于 1:大于 不比较时分秒
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static int compareDate(Date left, Date right) {
		Calendar temp = Calendar.getInstance(Locale.getDefault());
		Calendar c1 = Calendar.getInstance(Locale.getDefault());
		;
		Calendar c2 = Calendar.getInstance(Locale.getDefault());
		;
		c1.setTime(temp.getTime());
		c2.setTime(temp.getTime());

		temp.setTime(left);
		c1.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
		temp.setTime(right);
		c2.set(temp.get(Calendar.YEAR), temp.get(Calendar.MONTH), temp.get(Calendar.DAY_OF_MONTH));
		return c1.compareTo(c2);
	}

	/**
	 * 与今天相比较 0:相等 -1:小于 1:大于
	 * 
	 * @param dateTime
	 * @return
	 */
	public static int compareTodayTime(long dateTime) {
		Long currentTime = Calendar.getInstance(Locale.getDefault()).getTime().getTime();
		return -currentTime.compareTo(dateTime);
	}

	/**
	 * 
	 * @Description: 获得当月天数
	 * @param
	 * @return int
	 * @throws
	 */
	public static int getMonthDays() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		int num = c.get(Calendar.DATE);
		return num;
	}

	/**
	 * 
	 * @Description: 获得两个日期之间的天数
	 * @param
	 * @return int
	 * @throws
	 */
	public static int getDateInterval(Date start, Date end) {
		long quot;
		quot = end.getTime() - start.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		int day = new Long(quot).intValue();
		return day;
	}

	/**
	 * 
	 * @Description: 获得两个时间之间的间隔，单位小时
	 * @param
	 * @return int
	 * @throws
	 */
	public static int getDateIntervalByHour(Date start, Date end) {
		long quot;
		quot = end.getTime() - start.getTime();
		quot = quot / 1000 / 60 / 60;
		int hours = new Long(quot).intValue();
		return hours;
	}

	/**
	 * 
	 * @Description: 获得两个时间之间的间隔，单位分
	 * @param
	 * @return int
	 * @throws
	 */
	public static int getDateIntervalByMinute(Date start, Date end) {
		long quot;
		quot = end.getTime() - start.getTime();
		quot = quot / 1000 / 60 ;
		int minute = new Long(quot).intValue();
		return minute;
	}

	/**
	 * 是否是同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		boolean resulte = false;
		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
			resulte = true;
		}
		return resulte;
	}

	/**
	 * 将yyyy/MM/d hh:mm:ss或yyyy-MM-d hh:mm:ss 非标准转化成 yyyy-MM-dd hh:mm:ss格式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String changeDateFormat(String dateStr) {
		String resulte = null;

		if (dateStr == null) {
			return null;
		}
		dateStr = dateStr.replace("T", " ");

		String y = "";
		String M = "";
		String d = "";
		String h = "";

		String m = "";

		String s = "";

		String sqlit = "";

		if (dateStr != null && !dateStr.isEmpty()) {
			if (dateStr.contains("/")) {
				sqlit = "/";
			} else if (dateStr.contains("-")) {
				sqlit = "-";
			} else {
				return null;
			}
		} else {
			return null;
		}
		try {
			String[] datearray = dateStr.split(" ");

			if (datearray.length == 1) {

				String date = datearray[0];
				String[] dates = date.split(sqlit);
				y = dates[0];
				M = dates[1];
				d = dates[2];

				if (y.length() < 4) {
					return null;
				}
				int _M = Integer.parseInt(M);
				int _d = Integer.parseInt(d);
				if (_M < 10) {
					M = "0" + _M;
				}
				if (_d < 10) {
					d = "0" + _d;
				}

				h = "00";
				m = "00";
				s = "00";
			} else if (datearray.length == 2) {

				String date = datearray[0];
				String time = datearray[1];

				String[] dates = date.split(sqlit);
				y = dates[0];
				M = dates[1];
				d = dates[2];
				if (y.length() < 4) {
					return null;
				}
				int _M = Integer.parseInt(M);
				int _d = Integer.parseInt(d);
				if (_M < 10) {
					M = "0" + _M;
				}
				if (_d < 10) {
					d = "0" + _d;
				}

				String[] times = time.split(":");
				if (times.length == 3) {
					h = times[0];
					m = times[1];
					s = times[2];
					int _h = Integer.parseInt(h);
					int _m = Integer.parseInt(m);
					int _s = (int) Double.parseDouble(s);
					if (_h < 10) {
						h = "0" + _h;
					}
					if (_m < 10) {
						m = "0" + _m;
					}
					if (_s < 10) {
						s = "0" + _s;
					} else {
						s = _s + "";
					}

				} else if (times.length == 2) {
					h = times[0];
					m = times[1];

					int _h = Integer.parseInt(h);
					int _m = Integer.parseInt(m);

					if (_h < 10) {
						h = "0" + _h;
					}
					if (_m < 10) {
						m = "0" + _m;
					}
					s = "00";
				} else {
					h = "00";
					m = "00";
					s = "00";
				}

			} else {
				return null;
			}
			resulte = y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s;

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return resulte;
	}

	/**
	 * 
	 * @Description: 比较两个日期
	 * @param d1
	 * @param d2
	 * @return int
	 * @throws
	 */
	public static int compareTwoDate(Date d1, Date d2) {
		if (d1 == null && d2 == null) {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(d1);
			c2.setTime(d2);
		} catch (Exception e) {
			return -10;
		}
		return c1.compareTo(c2);
	}

    /**
     * 根据当前的日期，返回本季度的第一天和下个月的第一天
     * @return
     */
    public static Date[] getDateFromAndDateToByCurSeason(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int monthNow = cal.get(Calendar.MONTH);
        int monthTo = monthNow + 1;
        int monthFrom = monthNow / 3 * 3;

        cal.set(Calendar.MONTH, monthFrom);
        Date dateFrom = cal.getTime();//month起始时间
        cal.set(Calendar.MONTH, monthTo);
        Date dateTo = cal.getTime();//month终止时间

        return new Date[]{dateFrom, dateTo};
    }

    public static Date[]  getDateFromAndDateToByMonthCountBack(int monthCountBack) {
        //从当前月份向前数monthCountBack个月份，如果当前月份为4，monthCountBack为0
        //那么就返回DateFrom 4.1 DateTo 5.1
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.MONTH, -monthCountBack);
        Date dateFrom = cal.getTime();//month起始时间
        cal.add(Calendar.MONTH, 1);
        Date dateTo = cal.getTime();//month终止时间
        return new Date[]{dateFrom, dateTo};
    }

    public static Date getDefaultCustomerBirthday(){
        Date birthday = new Date();
        birthday.setYear(birthday.getYear() - 30);
        birthday.setMonth(0);
        birthday.setDate(1);
        birthday.setHours(0);
        birthday.setMinutes(0);
        birthday.setSeconds(0);
        return birthday;
    }

    public static String getCurrentTimestampString(){
        return String.valueOf(new Date().getTime());
    }

    public static long getCurrentTimestampLong(){
        return new Date().getTime();
    }

    public static long getCurrentTimestampLongInSecond(){
        return (new Date().getTime()) / 1000;
    }

	public static long convertTimestampFromMilliToSecond(Long milliSecond){
		if (milliSecond == null){
			return 0;
		}
		return milliSecond / 1000;
	}

    public static long parseTimestampInSecond(long timestampInSecond){
        if ((Long.MAX_VALUE / 1000) > timestampInSecond) {
            return timestampInSecond * 1000;
        }else{
            return Long.MAX_VALUE;
        }
    }

    public static Date getCurrentTimestampInSecondToDate(){
        long d0 =  new Date().getTime() / 1000;
        long d1 = d0 * 1000;
        return new Date(d1);
    }

    public static Date parseTimestampInSecondToDate(long timestampInSecond){
        long d0;
        if ((Long.MAX_VALUE / 1000) > timestampInSecond) {
            d0 = timestampInSecond * 1000;
        }else{
            d0 = Long.MAX_VALUE;
        }
        return new Date(d0);
    }

	public static String parseSecondToFriendlyText(String second){
        StringBuilder sb = new StringBuilder();
        String ret = "0'";
        if (TextUtils.isEmpty(second)){
            return ret;
        }
        try {
            int spanI = Integer.valueOf(second).intValue();
            int secondI = spanI % 60;
            int minuteI = (spanI / 60) % 60;
            int hourI = spanI / 3600;
            if (hourI > 0){
                sb.append(String.valueOf(hourI)).append(" ");
            }
            if (minuteI > 0){
                sb.append(String.valueOf(minuteI)).append("'");
            }
            if (secondI > 0){
                sb.append(String.valueOf(secondI)).append("\"");
            }
        }catch (Exception e){
            sb.append(ret);
        }
        return sb.toString();
    }
}