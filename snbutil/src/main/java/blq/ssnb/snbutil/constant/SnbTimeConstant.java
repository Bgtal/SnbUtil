package blq.ssnb.snbutil.constant;

import androidx.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期: 2017/6/12
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间常量
 * ================================================
 * </pre>
 */

public class SnbTimeConstant {
    /**
     * 一毫秒
     */
    public static final long ONE_MILLISECONDS = 1L;
    /**
     * 一秒 = 1000毫秒
     */
    public static final long ONE_SECONDS = 1000 * ONE_MILLISECONDS;
    /**
     * 一分钟 = 60 秒
     */
    public static final long ONE_MINUTES = 60 * ONE_SECONDS;
    /**
     * 半小时 = 30 分钟
     */
    public static final long HALF_AN_HOUR = 30 * ONE_MINUTES;
    /**
     * 一小时 = 60 分钟
     */
    public static final long ONE_HOURS = 60 * ONE_MINUTES;
    /**
     * 半天 = 12 小时
     */
    public static final long HALF_A_DAY = 12 * ONE_HOURS;
    /**
     * 一天 = 24 小时
     */
    public static final long ONE_DAY = 24 * ONE_HOURS;
    /**
     * 一周 = 7 天
     */
    public static final long ONE_WEEK = 7 * ONE_DAY;
    /**
     * 一月 = 30 天
     */
    public static final long ONE_MONTH = ONE_DAY * 30;
    /**
     * 半年 = 365/2 天
     */
    public static final long HALF_A_YEAR = ONE_DAY * 365 >> 1;
    /**
     * 一年 = 365 天
     */
    public static final long ONE_YEAR = ONE_DAY * 365;

    @LongDef({ONE_SECONDS, ONE_MINUTES,
            HALF_AN_HOUR, ONE_HOURS,
            HALF_A_DAY, ONE_DAY,
            ONE_WEEK, ONE_MONTH,
            HALF_A_YEAR, ONE_YEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeUtil {
    }

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";
    public static final String MM_dd = "MM-dd";

}
