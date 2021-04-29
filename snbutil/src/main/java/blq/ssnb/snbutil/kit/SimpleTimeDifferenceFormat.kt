package blq.ssnb.snbutil.kit

import blq.ssnb.snbutil.constant.SnbTimeConstant

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/14
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间差format显示“刚刚”，“X分钟前”..等
 * 可以自定义提示语
 * ================================================
</pre> *
 */
class SimpleTimeDifferenceFormat : TimeDifferenceFormat("刚刚", SnbTimeConstant.ONE_MILLISECONDS) {
    /**
     * 设置默认的提示，当超过一毫秒到下一个时间间隔的提示
     * @param normalMsg 提示语
     * @return SimpleTimeDifferenceFormat
     */
    fun setNormalHint(normalMsg: String?): SimpleTimeDifferenceFormat {
        setNormalMsg(normalMsg!!, SnbTimeConstant.ONE_MILLISECONDS)
        return this
    }

    /**
     * 超过一分钟的提示 例如（%d分钟前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedASecondsHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_SECONDS, msg!!)
    }

    /**
     * 移除一秒为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedASecondsHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_SECONDS)
    }

    /**
     * 超过一分钟的提示 例如（%d分钟前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedAMinuteHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_MINUTES, msg!!)
    }

    /**
     * 移除一分钟为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedAMinuteHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_MINUTES)
    }

    /**
     * 超过半小时的提示 例如（ 半小时前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedHalfHourHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.HALF_AN_HOUR, msg!!)
    }

    /**
     * 移除半小时(30分钟)为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedHalfHourHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.HALF_AN_HOUR)
    }

    /**
     * 超过一小时的提示 例如（ %d小时前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedAnHoursHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_HOURS, msg!!)
    }

    /**
     * 移除小时为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedAnHoursHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_HOURS)
    }

    /**
     * 超过半天的提示 例如（ 半天前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedHalfDayHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.HALF_A_DAY, msg!!)
    }

    /**
     * 移除半天(12小时)为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedHalfDayHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.HALF_A_DAY)
    }

    /**
     * 超过一天的提示 例如（%d 天前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedOneDayHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_DAY, msg!!)
    }

    /**
     * 移除天为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedOneDayHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_DAY)
    }

    /**
     * 超过一周月的提示 例如（%d 周前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedAWeekHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_WEEK, msg!!)
    }

    /**
     * 移除周为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedAWeekHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_WEEK)
    }

    /**
     * 超过一个月的提示 例如（%d 月前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedAMonthHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_MONTH, msg!!)
    }

    /**
     * 移除月为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedAMonthHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_MONTH)
    }

    /**
     * 超过半年的提示 例如（半年前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedHalfYearHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.HALF_A_YEAR, msg!!)
    }

    /**
     * 移除半年为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedHalfYearHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.HALF_A_YEAR)
    }

    /**
     * 超过一年的提示 例如（%d 年前）
     * @param msg 返回内容
     * @return SimpleTimeDifferenceFormat
     */
    fun setExceedYearHint(msg: String?): SimpleTimeDifferenceFormat? {
        return addFormat(SnbTimeConstant.ONE_YEAR, msg!!)
    }

    /**
     * 移除年为单位的提示
     * @return SimpleTimeDifferenceFormat
     */
    fun removeExceedYearHint(): SimpleTimeDifferenceFormat? {
        return removeFormat(SnbTimeConstant.ONE_YEAR)
    }

    init {
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_SECONDS, "%d秒前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_MINUTES, "%d分钟前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.HALF_AN_HOUR, "半小时前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_HOURS, "%d小时前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.HALF_A_DAY, "半天前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_DAY, "%d天前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_WEEK, "%d星期前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_MONTH, "%d月前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.HALF_A_YEAR, "半年前")
        addFormat<TimeDifferenceFormat>(SnbTimeConstant.ONE_YEAR, "%d年前")
    }
}