package blq.ssnb.snbutil.constant

import androidx.annotation.LongDef

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
</pre> *
 */
object SnbTimeConstant {
    /**
     * 一毫秒
     */
    const val ONE_MILLISECONDS = 1L

    /**
     * 一秒 = 1000毫秒
     */
    const val ONE_SECONDS = 1000 * ONE_MILLISECONDS

    /**
     * 一分钟 = 60 秒
     */
    const val ONE_MINUTES = 60 * ONE_SECONDS

    /**
     * 半小时 = 30 分钟
     */
    const val HALF_AN_HOUR = 30 * ONE_MINUTES

    /**
     * 一小时 = 60 分钟
     */
    const val ONE_HOURS = 60 * ONE_MINUTES

    /**
     * 半天 = 12 小时
     */
    const val HALF_A_DAY = 12 * ONE_HOURS

    /**
     * 一天 = 24 小时
     */
    const val ONE_DAY = 24 * ONE_HOURS

    /**
     * 一周 = 7 天
     */
    const val ONE_WEEK = 7 * ONE_DAY

    /**
     * 一月 = 30 天
     */
    const val ONE_MONTH = ONE_DAY * 30

    /**
     * 半年 = 365/2 天
     */
    const val HALF_A_YEAR = ONE_DAY * 365 shr 1

    /**
     * 一年 = 365 天
     */
    const val ONE_YEAR = ONE_DAY * 365
    const val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val HH_mm_ss = "HH:mm:ss"
    const val HH_mm = "HH:mm"
    const val MM_dd = "MM-dd"

    @LongDef(ONE_SECONDS, ONE_MINUTES, HALF_AN_HOUR, ONE_HOURS, HALF_A_DAY, ONE_DAY, ONE_WEEK, ONE_MONTH, HALF_A_YEAR, ONE_YEAR)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class TimeUtil
}