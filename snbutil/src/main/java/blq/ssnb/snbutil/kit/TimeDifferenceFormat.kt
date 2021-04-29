package blq.ssnb.snbutil.kit

import android.util.LongSparseArray
import blq.ssnb.snbutil.constant.SnbTimeConstant
import blq.ssnb.snbutil.constant.SnbTimeConstant.TimeUtil
import java.util.*
import kotlin.collections.HashMap

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间差的format 用于自定义不同时间差的显示内容
 * 目前请直接使用 [SimpleTimeDifferenceFormat]
 * ================================================
</pre> *
 */
open class TimeDifferenceFormat @JvmOverloads internal constructor(var normalMsg: String = "%ds", var normalLimit: Long = SnbTimeConstant.ONE_SECONDS) {
    private val formatKeyValue: LongSparseArray<String> = LongSparseArray()
    fun setNormalMsg(normalMsg: String, normalLimit: Long) {
        this.normalMsg = normalMsg
        this.normalLimit = normalLimit
    }

    /**
     * 添加判断条件
     * 例：添加 addFormat(SnbTimeConstant.ONE_MINUTES,"%d分钟前");
     * addFormat(SnbTimeConstant.HALF_AN_HOUR,"半小时前");
     * 当时间为8分钟的时候会显示8分钟前，当时间为38分钟的时候会显示半小时前
     * @param upLimit 时间上限
     * @param intervalMsg 时间区间内返回的内容,msg 中可以包含且只能包含一个"%d" 并且%d 的值是当前时间上线的单位个数
     * 如果时间上限为30分钟 输入的时间是36分钟如果{intervalMsg} 中包含%d 那么format  的值为36，
     * 如果时间上限为60分钟(即1小时) 输入的时间是36分钟如果{intervalMsg} 中包含%d 那么format  的值为0,
     * @return TimeDifferenceFormat
     */
    fun <T : TimeDifferenceFormat?> addFormat(@TimeUtil upLimit: Long, intervalMsg: String): T {
        if (intervalMsg.split(PLACE_HOLDER).toTypedArray().size > 2) {
            throw IllegalAccessError("提示中最多只能出现一次$PLACE_HOLDER")
        }
        formatKeyValue.put(upLimit, intervalMsg)
        return this as T
    }

    /**
     * 移除判断时间
     * @param upLimit 上限时间
     * @param <T> TimeDifferenceFormat 的继承类 目前只有[SimpleTimeDifferenceFormat]
     * @return TimeDifferenceFormat
    </T> */
    fun <T : TimeDifferenceFormat?> removeFormat(upLimit: Long): T {
        formatKeyValue.remove(upLimit)
        return this as T
    }

    fun format(differenceTime: Long): String {
        if (formatKeyValue.size() == 0) {
            return formatBorder(normalLimit, differenceTime, normalMsg)
        }
        var formatStr: String? = null
        for (i in formatKeyValue.size() - 1 downTo 0) {
            //获取边界值
            val limit = formatKeyValue.keyAt(i)
            val format = formatKeyValue.valueAt(i)
            if (differenceTime >= limit) {
                formatStr = formatBorder(limit, differenceTime, format)
                break
            }
        }
        if (formatStr == null) {
            formatStr = formatBorder(normalLimit, differenceTime, normalMsg)
        }
        return formatStr
    }

    private fun formatBorder(borderLimit: Long, differenceTime: Long, format: String): String {
        return if (format.contains(PLACE_HOLDER)) {
            //获得边界单位级别
            val unit = getLimitUnit(borderLimit)
            val count = (differenceTime / unit).toInt()
            String.format(Locale.getDefault(), format, count)
        } else {
            format
        }
    }

    private fun getLimitUnit(limit: Long): Long {
        //除以1000<1 表示毫秒级别
        var temp = (limit / SnbTimeConstant.ONE_SECONDS).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_MILLISECONDS
        }

        //除以60*1000 <1 表示秒级别
        temp = (limit / SnbTimeConstant.ONE_MINUTES).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_SECONDS
        }

        //除以60*60*1000 <1 表示分钟级别
        temp = (limit / SnbTimeConstant.ONE_HOURS).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_MINUTES
        }

        //除以24*60*60*1000 <1 表示小时级别
        temp = (limit / SnbTimeConstant.ONE_DAY).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_HOURS
        }
        //除以7*24*60*60*1000 <1 表示天级别
        temp = (limit / SnbTimeConstant.ONE_WEEK).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_DAY
        }

        //除以30*24*60*60*1000 < 1 表示周级别
        temp = (limit / SnbTimeConstant.ONE_MONTH).toInt()
        if (temp < 1) {
            return SnbTimeConstant.ONE_WEEK
        }

        //除以365*24*60*60*1000 < 1 表示月级别
        temp = (limit / SnbTimeConstant.ONE_YEAR).toInt()
        return if (temp < 1) {
            SnbTimeConstant.ONE_MONTH
        } else SnbTimeConstant.ONE_YEAR
    }

    companion object {
        private const val PLACE_HOLDER = "%d"
    }
}