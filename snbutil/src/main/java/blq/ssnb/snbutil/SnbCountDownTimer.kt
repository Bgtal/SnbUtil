package blq.ssnb.snbutil

import android.os.*
import java.lang.ref.WeakReference

/**
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * 作者：SSNB
 * 日期：2017/7/20
 * 邮箱：blq_ssnb@outlook.com
 * 修改次数：1
 * 描述：
 * 倒计时
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 */
abstract class SnbCountDownTimer constructor(
        /**
         * 总的倒计时时间，初始化后不会变了
         */
        private val mTotalTime: Long,
        /**
         * 倒计时间隔，初始化后就不变了
         */
        private val mCountdownInterval: Long) {
    enum class Status {
        /**
         * 计时器运行时
         */
        RUNNING,

        /**
         * 计时器暂停时
         */
        PAUSE,

        /**
         * 计时器结束时
         */
        FINISH
    }

    companion object {
        private const val MSG: Int = 1
    }

    /**
     * 最终截止时间 = 剩余时间+当前时间(系统启动时间)
     */
    private var mStopTimeInFuture: Long = 0

    /**
     * 暂停时候剩余时间
     */
    private var mPauseRemainingTime: Long = -1
    /**
     * 获得计时器状态
     *
     * @return [Status]
     */
    /**
     * 倒计时 状态
     */
    var timerStatus: Status
        private set

    /**
     * 用来倒计时的Handler
     */
    private val mHandler: Handler


    init {
        // 解决秒数有时会一开始就减去了2秒问题（如10秒总数的，刚开始就8999，然后没有不会显示9秒，直接到8秒）
//        if (countDownInterval >= 1000) totalTime += 15;
        mHandler = CountDownHandler(this)
        timerStatus = Status.FINISH //初始状态为结束状态
    }

    /**
     * 开始倒计时
     */
    @Synchronized
    fun start() {
        toStart()
    }

    /**
     * 重新开始计时.
     */
    @Synchronized
    fun restart() {
        initFinishStatus()
        toStart()
    }

    /**
     * 暂时倒计时
     */
    @Synchronized
    fun pause() {
        if (timerStatus == Status.RUNNING) { //只有对正在运行状态下有效
            timerStatus = Status.PAUSE
            //暂停时间 = 总时间 - 系统启动时间；
            mPauseRemainingTime = mStopTimeInFuture - SystemClock.elapsedRealtime()
            mHandler.removeMessages(MSG)
        }
    }

    /**
     * 停止倒计时
     */
    @Synchronized
    fun stop() {
        initFinishStatus()
    }

    private fun initFinishStatus() {
        timerStatus = Status.FINISH
        mPauseRemainingTime = -1
        mHandler.removeMessages(MSG)
    }

    /**
     * 启动倒计时
     * 如果正处于倒计时状态那么不会有任何效果
     * 如果处于暂停状态下会按暂停时剩下的时间继续执行，
     * 如果需要从新开始计时，调用[.restart]
     * 或先调用[.stop] 然后调用 [.start]
     */
    @Synchronized
    private fun toStart() {
        if (timerStatus == Status.RUNNING) { //如果正在倒计时就返回
            return
        }
        var remainingTime: Long = mTotalTime
        if (timerStatus == Status.PAUSE) { //如果是暂停状态
            if (mPauseRemainingTime > 0) { //暂停的剩余时间大于0
                remainingTime = mPauseRemainingTime
            } else { //否者就认为结束
                initFinishStatus()
                onFinish()
                return
            }
        }
        timerStatus = Status.RUNNING
        mStopTimeInFuture = SystemClock.elapsedRealtime() + remainingTime
        mHandler.sendMessage(mHandler.obtainMessage(MSG))
    }

    /**
     * 倒计时间隔回调
     *
     * @param remainingMillisecond 剩余毫秒数
     */
    protected abstract fun onTick(remainingMillisecond: Long)

    /**
     * 倒计时结束回调
     */
    protected abstract fun onFinish()

    private class CountDownHandler internal constructor(countDownTimer: SnbCountDownTimer) : Handler() {
        private val weakReference: WeakReference<SnbCountDownTimer>?

        init {
            weakReference = WeakReference(countDownTimer)
        }

        override fun handleMessage(msg: Message) {
            val timer: SnbCountDownTimer = weakReference?.get() ?: return

            synchronized(timer) {
                if (timer.timerStatus == Status.RUNNING) {
                    //获得当前的时间点
                    val startTime: Long = SystemClock.elapsedRealtime()
                    //还剩多少时间 = 截止时间- 当前时间
                    val remainingTime: Long = timer.mStopTimeInFuture - startTime
                    if (remainingTime < 0) {
                        //小于0，结束并发送结束通知
                        timer.initFinishStatus()
                        timer.onFinish()
                        if (remainingTime < timer.mStopTimeInFuture) {
                            sendMessageDelayed(obtainMessage(MSG), remainingTime)
                        }
                    } else {
                        //否者将剩余时间发出
                        timer.onTick(remainingTime)
                        //需要延迟发送的时间 = 当前是按 + 间隔是按 - 当前时间
                        var delay: Long = startTime + timer.mCountdownInterval - SystemClock.elapsedRealtime()
                        while (delay < 0) {
                            delay += timer.mCountdownInterval
                        }
                        sendMessageDelayed(obtainMessage(MSG), delay)
                    }
                }
            }
        }

    }

}
