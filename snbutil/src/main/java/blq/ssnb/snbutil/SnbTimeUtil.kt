package blq.ssnb.snbutil

import blq.ssnb.snbutil.constant.SnbTimeConstant
import blq.ssnb.snbutil.kit.TimeDifferenceFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间工具 提供了最基础的方法
 * 1.date或时间戳转换为格式化后的时间
 * 2.格式化后的时间转换为date或时间戳
 * 3.判断闰年
 * 4.两个时间之间的时间差
 * 5.格式化时间差
 * ================================================
</pre> *
 */
object SnbTimeUtil {
    /**
     * SimpleDateFormat 的缓存对象
     */
    private val FORMAT_CACHE: MutableMap<String, SimpleDateFormat> = HashMap()

    /**
     * 格式化时间
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 [.milliseconds2StringSafe]
     * @param format 时间格式
     * @param time 要格式化的时间戳
     * @return 格式化后的时间
     *
     * @see .date2String
     */
    @JvmStatic
    fun milliseconds2String(format: String, time: Long): String {
        return date2String(format, Date(time))
    }

    /**
     * 格式化时间,建议在多线程中调用该方法
     * 该方法会new新的SimpleDateFormat而不是从缓存中获取
     * 保证时间不会错乱
     * @param format 时间格式
     * @param time 要格式化的时间戳
     * @return 格式化后的时间
     */
    @JvmStatic
    fun milliseconds2StringSafe(format: String, time: Long): String {
        return date2StringSafe(format, Date(time))
    }

    @JvmStatic
    fun seconds2String(format: String, secondTime: Long): String {
        return milliseconds2String(format, secondTime * SnbTimeConstant.ONE_SECONDS)
    }

    @JvmStatic
    fun seconds2StringSafe(format: String, secondTime: Long): String {
        return milliseconds2StringSafe(format, secondTime * SnbTimeConstant.ONE_SECONDS)
    }

    /**
     * 格式化时间
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 [.date2StringSafe]
     * @param format 时间格式
     * @param date 要格式化的Date
     * @return 格式化后的时间
     *
     * @see SimpleDateFormat
     *
     * Date formats are not synchronized.
     * It is recommended to create separate format instances for each thread.
     * If multiple threads access a format concurrently, it must be synchronized
     * externally.
     */
    @JvmStatic
    fun date2String(format: String, date: Date?): String {
        return date2String(format, date, true)
    }

    /**
     * 格式化时间,建议在多线程中调用该方法
     * 该方法会new新的SimpleDateFormat而不是从缓存中获取
     * 保证时间不会错乱
     * @param format 时间格式
     * @param date 要格式化的Date
     * @return 格式化后的时间
     */
    @JvmStatic
    fun date2StringSafe(format: String, date: Date?): String {
        return date2String(format, date, false)
    }

    /**
     * 格式化时间
     * @param format 时间格式
     * @param date 要格式化的时间
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     * 否者就new 一个SimpleDateFormat
     * @return 格式化后的时间
     */
    @JvmStatic
    private fun date2String(format: String, date: Date?, isSafe: Boolean): String {
        if (date == null) {
            throw NullPointerException("date == null")
        }
        val sf = getSimpleDateFormat(isSafe, format)
        return sf.format(date)
    }

    /**
     * 将格式化后的时间转换为时间戳
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 [.String2MillisecondSafe]
     * @see SnbTimeUtil.String2Millisecond
     */
    @JvmStatic
    @Throws(ParseException::class)
    fun String2Date(time: String, format: String): Date {
        return Date(String2Millisecond(time, format))
    }

    /**
     * @see SnbTimeUtil.String2MillisecondSafe
     */
    @JvmStatic
    @Throws(ParseException::class)
    fun String2DateSafe(time: String, format: String): Date {
        return Date(String2MillisecondSafe(time, format))
    }

    /**
     * 将格式化后的时间转换为时间戳
     * 保证线程是安全的状态下调用（一般性不会出错）
     * 如果是在多线程或中调用建议使用 [.String2MillisecondSafe]
     * @param time 文字时间（例如 2017年6月12日11:25:53）
     * @param format 与文字时间对应的时间格式 （例如 yyyy年MM月dd日 HH:mm:ss）
     * @return 转换后的时间戳
     * @throws ParseException 如果文字时间格式和 format 格式不对应会解析错误
     */
    @JvmStatic
    @Throws(ParseException::class)
    fun String2Millisecond(time: String, format: String): Long {
        return String2Millisecond(time, format, true)
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
    @JvmStatic
    @Throws(ParseException::class)
    fun String2MillisecondSafe(time: String, format: String): Long {
        return String2Millisecond(time, format, false)
    }

    /**
     * 将格式化后的时间转换为时间戳
     * @param time 文字时间（例如 2017年6月12日11:25:53）
     * @param format 文字格式
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     * 否者就new 一个SimpleDateFormat
     * @return 转换后的时间
     * @throws ParseException 如果转换错误就会报错
     */
    @JvmStatic
    @Throws(ParseException::class)
    private fun String2Millisecond(time: String, format: String, isSafe: Boolean): Long {
        val sf = getSimpleDateFormat(isSafe, format)
        return try {
            sf.parse(time).time
        } catch (e: ParseException) {
            throw ParseException("""
                    ${e.message}
                    format:$format
                    time:$time
                    """.trimIndent(), e.errorOffset)
        }
    }

    /**
     * 获得SimpleDateFormat对象
     * @param isSafe 是否安全 true 表示安全状态会从#formatCache中获取SimpleDateFormat
     * 否者就new 一个SimpleDateFormat
     * @param format 格式化样式
     * @return SimpleDateFormat 对象
     */
    @JvmStatic
    private fun getSimpleDateFormat(isSafe: Boolean, format: String): SimpleDateFormat {
        val sf: SimpleDateFormat
        if (isSafe) {
            //如果线程安全就调用缓存里面的
            if (FORMAT_CACHE[format] != null) {
                sf = SimpleDateFormat(format, Locale.getDefault())
                FORMAT_CACHE[format] = sf
            } else {
                sf = FORMAT_CACHE[format]!!
            }
        } else { //如果线程是不安全的那么就实时创建
            sf = SimpleDateFormat(format, Locale.getDefault())
        }
        return sf
    }

    /**
     * 判断是否是闰年
     * @param timeStamp 时间戳
     * @return true 闰年 反之返回false
     */
    @JvmStatic
    fun isLeapYear(timeStamp: Long): Boolean {
        val yearStr = milliseconds2String("yyyy", timeStamp)
        val year = Integer.valueOf(yearStr)
        return isLeapYear(year)
    }

    /**
     * 是否是闰年
     * @param year 年份（2017）
     * @return true 闰年 反之返回false
     */
    @JvmStatic
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 两个时间戳之间的时间差
     * @param timeOne 第一个时间点
     * @param timeTwo 第二个时间点
     * @return 时间差 为非零数
     */
    @JvmStatic
    fun timeDifference(timeOne: Long, timeTwo: Long): Long {
        return abs(timeOne - timeTwo)
    }

    /**
     * 两个日期之间的时间差，
     * 如果传入的参数 == null ，那么该参数时间就为0
     * @param timeOne 第一个日期
     * @param timeTwo 第二个日期
     * @return 时间差
     */
    @JvmStatic
    fun timeDifference(timeOne: Date?, timeTwo: Date?): Long {
        val timeOneLong = timeOne?.time ?: 0
        val timeTwoLong = timeTwo?.time ?: 0
        return timeDifference(timeOneLong, timeTwoLong)
    }

    @JvmStatic
    fun timeDifferenceFormat(differenceTime: Long, format: TimeDifferenceFormat): String {
        return format.format(differenceTime)
    }
}