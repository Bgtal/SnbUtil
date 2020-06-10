package blq.ssnb.snbutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import blq.ssnb.snbutil.constant.SnbTimeConstant;
import blq.ssnb.snbutil.kit.TimeDifferenceFormat;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间工具 提供了最基础的方法
 *      1.date或时间戳转换为格式化后的时间
 *      2.格式化后的时间转换为date或时间戳
 *      3.判断闰年
 *      4.两个时间之间的时间差
 *      5.格式化时间差
 * ================================================
 * </pre>
 */

public class SnbTimeUtil {
    /**
     * SimpleDateFormat 的缓存对象
     */
    private static final Map<String,SimpleDateFormat> FORMAT_CACHE = new HashMap<>();

    private SnbTimeUtil(){
        throw new SnbIllegalInstantiationException();
    }
    /**
     * 格式化时间
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 {@link #milliseconds2StringSafe(String, long)}
     * @param format 时间格式
     * @param time 要格式化的时间戳
     * @return 格式化后的时间
     *
     * @see #date2String
     */
    public static String milliseconds2String(String format, long time){
        return date2String(format,new Date(time));
    }

    /**
     * 格式化时间,建议在多线程中调用该方法
     * 该方法会new新的SimpleDateFormat而不是从缓存中获取
     * 保证时间不会错乱
     * @param format 时间格式
     * @param time 要格式化的时间戳
     * @return 格式化后的时间
     */
    public static String milliseconds2StringSafe(String format, long time){
        return date2StringSafe(format,new Date(time));
    }

    public static String seconds2String(String format,long secondTime){
        return milliseconds2String(format,secondTime * SnbTimeConstant.ONE_SECONDS);
    }

    public static String seconds2StringSafe(String format,long secondTime){
        return milliseconds2StringSafe(format,secondTime * SnbTimeConstant.ONE_SECONDS);
    }

    /**
     * 格式化时间
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 {@link #date2StringSafe(String, Date)}
     * @param format 时间格式
     * @param date 要格式化的Date
     * @return 格式化后的时间
     *
     * @see SimpleDateFormat
     * <p>Date formats are not synchronized.
     * It is recommended to create separate format instances for each thread.
     * If multiple threads access a format concurrently, it must be synchronized
     * externally.
     */
    public static String date2String(String format, Date date){
        return date2String(format, date,true);
    }

    /**
     * 格式化时间,建议在多线程中调用该方法
     * 该方法会new新的SimpleDateFormat而不是从缓存中获取
     * 保证时间不会错乱
     * @param format 时间格式
     * @param date 要格式化的Date
     * @return 格式化后的时间
     */
    public static String date2StringSafe(String format, Date date){
        return date2String(format, date,false);
    }

    /**
     * 格式化时间
     * @param format 时间格式
     * @param date 要格式化的时间
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     *               否者就new 一个SimpleDateFormat
     * @return 格式化后的时间
     */
    private static String date2String(String format, Date date, boolean isSafe){

        if(date==null){
            throw new NullPointerException("date == null");
        }
        SimpleDateFormat sf = getSimpleDateFormat(isSafe, format) ;
        return sf.format(date);
    }

    /**
     * 将格式化后的时间转换为时间戳
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 {@link #String2MillisecondSafe(String, String)}
     * @see SnbTimeUtil#String2Millisecond(String, String)
     */
    public static Date String2Date(String time, String format)  throws ParseException {
        return new Date(String2Millisecond(time, format));
    }

    /**
     * @see SnbTimeUtil#String2MillisecondSafe(String, String)
     */
    public static Date String2DateSafe(String time, String format)  throws ParseException {
        return new Date(String2MillisecondSafe(time, format));
    }

    /**
     * 将格式化后的时间转换为时间戳
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 {@link #String2MillisecondSafe(String, String)}
     * @param time 文字时间（例如 2017年6月12日11:25:53）
     * @param format 与文字时间对应的时间格式 （例如 yyyy年MM月dd日 HH:mm:ss）
     * @return 转换后的时间戳
     * @throws ParseException 如果文字时间格式和 format 格式不对应会解析错误
     */
    public static long String2Millisecond(String time, String format) throws ParseException {
        return String2Millisecond(time, format,true);
    }

    /**
     *
     * 格式化时间,建议在多线程中调用该方法
     * 该方法会new新的SimpleDateFormat而不是从缓存中获取
     * @param time 文字时间（例如 2017年6月12日11:25:53）
     * @param format 与文字时间对应的时间格式 （例如 yyyy年MM月dd日 HH:mm:ss）
     * @return 转换后的时间戳
     * @throws ParseException 如果文字时间格式和 format 格式不对应会解析错误
     */
    public static long String2MillisecondSafe(String time, String format) throws ParseException {
        return String2Millisecond(time, format,false);
    }

    /**
     *  将格式化后的时间转换为时间戳
     * @param time 文字时间（例如 2017年6月12日11:25:53）
     * @param format 文字格式
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     *               否者就new 一个SimpleDateFormat
     * @return 转换后的时间
     * @throws ParseException 如果转换错误就会报错
     */
    private static long String2Millisecond(String time, String format, boolean isSafe) throws ParseException {
        SimpleDateFormat sf =getSimpleDateFormat(isSafe, format);
        try{
            return sf.parse(time).getTime();
        }catch (ParseException e){
            throw new ParseException(e.getMessage()+"\n"+"format:"+format+"\ntime:"+time,e.getErrorOffset());
        }
    }

    /**
     * 获得SimpleDateFormat对象
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     *               否者就new 一个SimpleDateFormat
     * @param format 格式化样式
     * @return SimpleDateFormat 对象
     */
    private static SimpleDateFormat getSimpleDateFormat(boolean isSafe, String format){
        SimpleDateFormat sf ;
        if(isSafe){
            //如果线程安全就调用缓存里面的
            sf = FORMAT_CACHE.get(format);
            if(sf == null){
                sf = new SimpleDateFormat(format, Locale.getDefault());
                FORMAT_CACHE.put(format,sf);
            }
        }else{//如果线程是不安全的那么就实时创建
            sf = new SimpleDateFormat(format, Locale.getDefault());
        }
        return sf;
    }


    /**
     * 判断是否是闰年
     * @param timeStamp 时间戳
     * @return true 闰年 反之返回false
     */
    public static boolean isLeapYear(long timeStamp){
        String yearStr = milliseconds2String("yyyy",timeStamp);
        int year = Integer.valueOf(yearStr);
        return isLeapYear(year);
    }

    /**
     * 是否是闰年
     * @param year 年份（2017）
     * @return true 闰年 反之返回false
     */
    public static boolean isLeapYear(int year){
        return (year % 4 == 0 && year %100 !=0) || (year % 400 == 0);
    }

    /**
     * 两个时间戳之间的时间差
     * @param timeOne 第一个时间点
     * @param timeTwo 第二个时间点
     * @return 时间差 为非零数
     */
    public static long timeDifference(long timeOne ,long timeTwo){
        return Math.abs(timeOne-timeTwo);
    }

    /**
     * 两个日期之间的时间差，
     * 如果传入的参数 == null ，那么该参数时间就为0
     * @param timeOne 第一个日期
     * @param timeTwo 第二个日期
     * @return 时间差
     */
    public static long timeDifference(Date timeOne , Date timeTwo){
        long timeOneLong =timeOne == null?0:timeOne.getTime();
        long timeTwoLong =timeTwo == null?0:timeTwo.getTime();
        return timeDifference(timeOneLong,timeTwoLong);
    }

    public static String timeDifferenceFormat(long differenceTime , TimeDifferenceFormat format){
        return format.format(differenceTime);
    }

}