package cn.zifangsky.common;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateFormatUtil {
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String Y_M_D = "yyyy-MM-dd";
    /**
     * 日期格式：yyyy-MM
     */
    public static final String Y_M = "yyyy-MM";
    /**
     * 日期格式：yyyy
     */
    public static final String Y = "yyyy";
    /**
     * 日期格式：yyyyMMddHHmmss
     */
    public static final String YMDHMS = "yyyyMMddHHmmss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyyMM
     */
    public static final String YM = "yyyyMM";
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String YMD = "yyyyMMdd";
    /**
     * 一天的开始,格式：" 00:00:00"
     */
    public static final String BEGIN_DAY = " 00:00:00";
    /**
     * 一天的结束,格式：" 23:59:59"
     */
    public static final String END_DAY = " 23:59:59";

    public static final String BEGIN_TIME = "000000";

    public static final String END_TIME = "235959";
    /**
     * 年初：1月1日
     */
    public static final Integer BEGIN_YEAR = 1;
    /**
     * 年末：12月31日
     */
    public static final Integer END_YEAR = 0;


    /**
     * Title:  getLastTimeInterval<br>
     * Description: 根据当前日期获得上周的日期区间（上周周一和周日日期） <br>
     *
     * @param forMatStr 日期格式

     */
    public static String getLastTimeInterval(String forMatStr) {
        SimpleDateFormat format = new SimpleDateFormat(forMatStr);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = format.format(calendar1.getTime());
        String lastEndDate = format.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }

    /**
     * Title:  getTimeInterval<br>
     * Description: 根据制定日期获得上周的日期区间（上周周一和周日日期）<br>
     *

     * @since JDK 1.7
     */
    public static String getTimeInterval(Date date, String forMatStr) {
        SimpleDateFormat format = new SimpleDateFormat(forMatStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = format.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = format.format(cal.getTime());
        return imptimeBegin + "," + imptimeEnd;
    }

    /**
     * 获取今天 日期 +00:00:00,今天 日期 +23:59:59
     *
     * @return
     */
    public static JSONObject getStartAndEndNowDay() {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        String startDateOfDay = format.format(new Date().getTime()) + BEGIN_DAY;
        String endDateOfDay = format.format(new Date().getTime()) + END_DAY;
        jo.put("start", startDateOfDay);
        jo.put("end", endDateOfDay);
        return jo;
    }

    /**
     * 获取昨天  日期+00:00:00,昨天 日期 +23:59:59
     *
     * @return
     */
    public static JSONObject getStartAndEndLastDay(Date date) {
        JSONObject jo = new JSONObject();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        String startDateOfDay = format.format(c.getTime()) + BEGIN_DAY;
        String endDateOfDay = format.format(c.getTime()) + END_DAY;
        jo.put("start", startDateOfDay);
        jo.put("end", endDateOfDay);
        return jo;
    }

    /**
     * 获取上周一,周日 日期
     *
     * @return
     */
    public static JSONObject getLastTimeInterval(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = format.format(calendar1.getTime());
        String lastEndDate = format.format(calendar2.getTime());
        jo.put("start", lastBeginDate + BEGIN_DAY);
        jo.put("end", lastEndDate + END_DAY);
        return jo;
    }

    /**
     * 获取本周一,周日 日期
     *
     * @return
     */
    public static JSONObject getNextTimeInterval(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar2.setTime(date);
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1);
        calendar2.add(Calendar.DATE, offset2);
        String lastBeginDate = format.format(calendar1.getTime());
        String lastEndDate = format.format(calendar2.getTime());
        jo.put("start", lastBeginDate + BEGIN_DAY);
        jo.put("end", lastEndDate + END_DAY);
        return jo;
    }

    /**
     * 获取上月开始日,上月结束日
     *
     * @return
     */
    public static JSONObject getStartAndEndOfMonth(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(YMD_HMS);
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.add(Calendar.MONTH, -1);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMinimum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMinimum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMinimum(Calendar.SECOND));
        String startDayOfMonth = format.format(new Date(ca.getTimeInMillis()));

        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMaximum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));
        String endDayOfMonth = format.format(new Date(ca.getTimeInMillis()));
        jo.put("start", startDayOfMonth);
        jo.put("end", endDayOfMonth);
        return jo;
    }

    /**
     * 获取下月开始日,上月结束日
     *
     * @return
     */
    public static JSONObject getStartAndEndOfNextMonth(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(YMD_HMS);
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.add(Calendar.MONTH, +1);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMinimum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMinimum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMinimum(Calendar.SECOND));
        String startDayOfMonth = format.format(new Date(ca.getTimeInMillis()));

        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMaximum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));
        String endDayOfMonth = format.format(new Date(ca.getTimeInMillis()));
        jo.put("start", startDayOfMonth);
        jo.put("end", endDayOfMonth);
        return jo;
    }

    /**
     * 获取指定月份时间
     *
     * @param date
     * @param monthAddNum 月份增加时间：负数向前推,正数向后推
     * @return 当前时间和推送的时间(start < end)
     */
    public static JSONObject getStartAndEndDayOfMonth(Date date, int monthAddNum) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String firstDayOfMonth = null;
        String lastDayOfMonth = null;
        if (monthAddNum < 0) {
            calendar.add(Calendar.MONTH, monthAddNum);
            firstDayOfMonth = format.format(new Date(calendar.getTimeInMillis()));
            lastDayOfMonth = format.format(date);
        } else {
            calendar.add(Calendar.MONTH, monthAddNum);
            firstDayOfMonth = format.format(date);
            lastDayOfMonth = format.format(new Date(calendar.getTimeInMillis()));
        }
        jo.put("start", firstDayOfMonth + BEGIN_DAY);
        jo.put("end", lastDayOfMonth + END_DAY);
        return jo;
    }

    /**
     * 获取本月 第一天,最后一天
     *
     * @param date
     * @return
     */
    public static JSONObject getStartAndEndDayOfMonth(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonth = format.format(new Date(calendar.getTimeInMillis()));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDayOfMonth = format.format(new Date(calendar.getTimeInMillis()));
        jo.put("start", firstDayOfMonth + BEGIN_DAY);
        jo.put("end", lastDayOfMonth + END_DAY);
        return jo;
    }

    /**
     * 获取本周 第一天,最后一天
     *
     * @param date
     * @return
     */
    public static JSONObject getStartAndEndDayOfWeek(Date date) {
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String firstDayOfWeek = format.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String lastDayOfWeek = format.format(calendar.getTime());
        jo.put("start", firstDayOfWeek + BEGIN_DAY);
        jo.put("end", lastDayOfWeek + END_DAY);
        return jo;
    }


    /**
     * 获取当前月有多少天
     *
     * @return
     */
    public static Integer getMonthByDayCount() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static Integer getMonthCount() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }


    public static Integer getMonthCount(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 截取日期 返回 月-日
     *
     * @param date
     * @return 01-01
     */
    public static String reMonthAndDay(String date) {
        return date.substring(5, 10);
    }

    /**
     * Title:  getPreNumDate<br>
     * Description: 获取当前日期向前/后的某天,以及当前时间,格式：{"start":向前/后天数,"end":当前时间}<br>

     * @since JDK 1.7
     */
    public static JSONObject getPreNumDate(Date date, int addDayNum) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, addDayNum);
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);

        JSONObject jo = new JSONObject();
        jo.put("start", format.format(ca.getTime()) + BEGIN_DAY);
        jo.put("end", format.format(date) + END_DAY);
        return jo;
    }

    /**
     * Title:  getPreDateJson<br>
     * Description: 获取当前日期向前/后的某天,以及当前时间,格式：{"start":向前/后天数开始时间,"end":向前/后天数结束时间}<br>
     *

     * @since JDK 1.7
     */
    public static JSONObject getPreDateJson(Date date, int addDayNum) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, addDayNum);
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);

        JSONObject jo = new JSONObject();
        String preDate = format.format(ca.getTime());
        jo.put("start", preDate + BEGIN_DAY);
        jo.put("end", preDate + END_DAY);
        return jo;
    }

    /**
     * 日期格式化
     *
     * @param str
     * @return
     */
    public static Date getStrToDate(String str, String sdf) {
        SimpleDateFormat format = new SimpleDateFormat(sdf);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Title:  getStartOrEndOfDay<br>
     * Description: 获取当天开始时间或者结束时间<br>
     *

     * @since JDK 1.6
     */
    public static String getStartOrEndOfDay(Date date, String startOrEnd) {
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        return format.format(date) + startOrEnd;
    }

    /**
     * Title:  getStartOrEndOfDay<br>
     * Description:获取向前/向后(添加/减少多少年多少月多少日)一天的开始和结束时间<br>
     *
     * @param date       当前日期
     * @param startOrEnd 一天的开始或者结尾标识
     * @param year       增加/减少年份
     * @param month      增加/减少月份
     * @param day        增加/减少天数
     * @return
     * @author sunwei
     * @Modified by
     * @CreateDate 2016年5月18日 上午10:52:15
     * @Version
     * @since JDK 1.7
     */
    public static String getStartOrEndOfDay(Date date, String startOrEnd, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DATE, day);
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        return format.format(c.getTime()) + startOrEnd;

    }

    /**
     * Title:  getStartOfMonth<br>
     * Description:获取月份第一天<br>
     *

     */
    public static String getStartOfMonth(Date date, int addMonthMount) {
        SimpleDateFormat format = new SimpleDateFormat(YMD_HMS);
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.add(Calendar.MONTH, addMonthMount);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMinimum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMinimum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMinimum(Calendar.SECOND));
        return format.format(new Date(ca.getTimeInMillis()));
    }

    /**
     * Title:  getEndOfMonth<br>
     * Description:获取月份最后一天<br>
     *

     */
    public static String getEndOfMonth(Date date, int addMonthMount) {
        SimpleDateFormat format = new SimpleDateFormat(YMD_HMS);
        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(date.getTime());
        ca.add(Calendar.MONTH, addMonthMount);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, ca.getActualMaximum(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));
        ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));
        return format.format(new Date(ca.getTimeInMillis()));
    }

    /**
     * Title:  getPreDate<br>
     * Description: 获取当前日期向前/后的某天<br>
     *

     */
    public static Date getPreDate(Date date, int addDayNum) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, addDayNum);
        return ca.getTime();
    }

    /**
     * Title:  getNeedDate<br>
     * Description: 获取当前日期向前/后的某天 的字符串<br>

     */
    public static String getNeedDate(Date date, int addDayNum, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, addDayNum);
        return format.format(ca.getTime());
    }

    /**
     * Title:  getPreDate<br>
     * Description: 获取当前日期向前/后的某天 的字符串<br>
     *
     * @param date       日期
     * @param addDayNum  添加/减少的天数
     * @param startOrEnd 一天开始或者结束的标识
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2016年4月27日 上午9:43:23
     * @Version
     * @since JDK 1.7
     */
    public static String getPreDate(Date date, int addDayNum, String startOrEnd) {
        SimpleDateFormat format = new SimpleDateFormat(Y_M_D);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_MONTH, addDayNum);
        return format.format(ca.getTime()) + startOrEnd;
    }

    /**
     * Title:  formatDateToStr<br>
     * Description: 将日志格式化为字符串<br>
     *
     * @param date      日志
     * @param formatStr 日期格式(默认：yyyy-MM-dd HH:mm:ss)
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2016年5月2日 下午12:20:09
     * @Version
     * @since JDK 1.7
     */
    public static String formatDateToStr(Date date, String formatStr) {
        if (date == null) {
            return null;
        }
        if (formatStr == null) {
            formatStr = YMD_HMS;
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * Title:  formatDateToStr<br>
     * Description: 将日志格式化为字符串(默认：yyyy-MM-dd HH:mm:ss)<br>
     *
     * @param date      日志
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2016年5月2日 下午12:20:09
     * @Version
     * @since JDK 1.7
     */
    public static String formatDateToStr(Date date) {
        return formatDateToStr(date, YMD_HMS);
    }

    public static String formatDateToStrYMD(Date date) {
        return formatDateToStr(date, Y_M_D);
    }


    /**
     * Title:  formatDateToStr<br>
     * Description: 格式化日期<br>
     *
     * @param date
     * @param formatStr
     * @param beginOrEnd 一天的开始或者结束,或者自定义
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2017年6月29日 下午3:47:06
     * @Version
     * @since JDK 1.7
     */
    public static String formatDateToStr(Date date, String formatStr, String beginOrEnd) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date) + beginOrEnd;
    }

    /**
     * Title:  parseStrToDate<br>
     * Description:将字符串格式化为日期<br>
     *
     * @param dateStr 日期字符串
     * @return
     * @author luyj
     * @Modified by
     * @CreateDate 2019年3月26日 下午21:23:35
     * @Version
     * @since JDK 1.7
     */
    public static Date parseStrToDate(String dateStr) {
        return parseStrToDate(dateStr, YMD_HMS);
    }

    /**
     * Title:  parseStrToDate<br>
     * Description:将字符串格式化为日期<br>
     *
     * @param dateStr   日期字符串
     * @param formatStr 日期格式
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2016年5月2日 下午12:23:35
     * @Version
     * @since JDK 1.7
     */
    public static Date parseStrToDate(String dateStr, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Title:  diffSub<br>
     * Description: 获取两个日期之间的月份差值<br>
     *
     * @param str1
     * @param str2
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2017年6月13日 上午10:48:12
     * @Version
     * @since JDK 1.7
     */
    public static int diffSub(String str1, String str2) {
        int result = 0;
        int year = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(YMD);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(format.parse(str1));
            Calendar c2 = Calendar.getInstance();
            c2.setTime(format.parse(str2));
            result = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
            year = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Math.abs(12 * year) + Math.abs(result);
    }

    /**
     * Title:  diffSubList<br>
     * Description:获取两个日期月份差<br>
     *
     * @param str1
     * @param str2
     * @return
     * @author wmy
     * @Modified by
     * @CreateDate 2017年6月13日 上午10:57:11
     * @Version
     * @since JDK 1.7
     */
    public static List<String> diffSubList(String str1, String str2) {
        List<String> monthList = new ArrayList<String>();
        try {
            SimpleDateFormat format = new SimpleDateFormat(Y_M);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(format.parse(str1));
            c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), 1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(format.parse(str2));
            c2.set(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), 2);
            if (c1.getTimeInMillis() > c2.getTimeInMillis()) {
                Calendar temp = c1;
                c1 = c2;
                c2 = temp;
            }
            Calendar curr = c1;
            while (curr.before(c2)) {
                monthList.add(format.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return monthList;
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     * @Title: getMonthSpace
     * @Description: 获取相差多少月
     * @date: 2018年1月15日 上午11:19:45
     * @author: sunwei
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

        return result == 0 ? 1 : Math.abs(result);

    }

    /**
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     * @Title: getDateSpace
     * @Description:获取两个日期之间相差的天数
     * @date: 2018年1月15日 上午11:23:02
     * @author: sunwei
     */
    public static int getDateSpace(String date1, String date2)
            throws ParseException {
        return getDateSpace(parseStrToDate(date1, Y_M_D), parseStrToDate(date2, Y_M_D));
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     * @Title: getDateSpace
     * @Description:获取两个日期之间相差的天数
     * @date: 2018年1月15日 上午11:23:02
     * @author: sunwei
     */
    public static int getDateSpace(Date date1, Date date2)
            throws ParseException {

        Calendar calst = Calendar.getInstance();
        ;
        Calendar caled = Calendar.getInstance();

        calst.setTime(date1);
        caled.setTime(date2);

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数   
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    /**
     * 比较时间大小
     *
     * @Params:
     * @Author:lizeqing
     * @Date:2019-03-13 19:58
     * @return: date1 > date2 返回 1
     * date1 < date2 返回-1
     * date1 = date2 返回0
     */
    public static int compareDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.compareTo(cal2);
    }

    /**
     * 比较时间大小
     *
     * @Params:
     * @Author:lizeqing
     * @Date:2019-03-13 19:58
     * @return: date1 > date2 返回 1
     * date1 < date2 返回-1
     * date1 = date2 返回0
     */
    public static int compareDate(String date1, String date2) {
        Date d1 = parseStrToDate(date1, Y_M_D);
        Date d2 = parseStrToDate(date2, Y_M_D);
        return compareDate(d1, d2);
    }

    /**
    * @description:计算多少分钟前的时间
    * @param i
    * @Author:zhaoJX
    * @Date:18:52 2019-04-17
    * @return:
    */
    public static Date getCurrentTime(int i){
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -i);
        Date beforeD = beforeTime.getTime();
        return beforeD;
    }


    /**
     * 清除date中的时间
     *
     * @param date
     * @return
     */
    public static Date getDateClearTime(Date date) {
        if (date == null) {
            return null;
        }
        return parseStrToDate(formatDateToStr(date, YMD));
    }

/*    public static void main(String[] args) {

        System.out.println(compareDate(new Date(), parseStrToDate("2019-04-01 00:00:00", "yyyy-MM-dd HH:mm:ss")));

        System.out.println(compareDate("2019-01-01", "2019-01-02"));

        System.out.println(getMonthCount());

        System.out.println(getDateClearTime(parseStrToDate("2019-04-01 02:10:00", "yyyy-MM-dd HH:mm:ss")));

    }*/
}
