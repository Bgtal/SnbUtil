package blq.ssnb.snbutil;

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

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

public abstract class SnbCountDownTimer {

    public enum Status {
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

    private static final int MSG = 1;

    /**
     * 总的倒计时时间，初始化后不会变了
     */
    private final long mTotalTime;

    /**
     * 倒计时间隔，初始化后就不变了
     */
    private final long mCountdownInterval;

    /**
     * 最终截止时间 = 剩余时间+当前时间(系统启动时间)
     */
    private long mStopTimeInFuture;

    /**
     * 暂停时候剩余时间
     */
    private long mPauseRemainingTime = -1;

    /**
     * 倒计时 状态
     */
    private Status mStatus;

    /**
     * 用来倒计时的Handler
     */
    private Handler mHandler;

    /**
     * @param totalTime         总倒计时时间
     * @param countDownInterval 倒计时间隔时间
     */
    public SnbCountDownTimer(long totalTime, long countDownInterval) {
        // 解决秒数有时会一开始就减去了2秒问题（如10秒总数的，刚开始就8999，然后没有不会显示9秒，直接到8秒）
//        if (countDownInterval >= 1000) totalTime += 15;
        mTotalTime = totalTime;
        mCountdownInterval = countDownInterval;
        mHandler = new CountDownHandler(this);
        mStatus = Status.FINISH;//初始状态为结束状态
    }


    /**
     * 开始倒计时
     */
    public synchronized final void start() {
        toStart();
    }

    /**
     * 重新开始计时.
     */
    public synchronized final void restart() {
        initFinishStatus();
        toStart();
    }

    /**
     * 暂时倒计时
     */
    public synchronized final void pause() {
        if (mStatus == Status.RUNNING) {//只有对正在运行状态下有效
            mStatus = Status.PAUSE;
            //暂停时间 = 总时间 - 系统启动时间；
            mPauseRemainingTime = mStopTimeInFuture - SystemClock.elapsedRealtime();
            mHandler.removeMessages(MSG);
        }
    }

    /**
     * 停止倒计时
     */
    public synchronized final void stop() {
        initFinishStatus();
    }

    /**
     * 获得计时器状态
     *
     * @return {@link Status}
     */
    public Status getTimerStatus() {
        return mStatus;
    }

    private void initFinishStatus() {
        mStatus = Status.FINISH;
        mPauseRemainingTime = -1;
        mHandler.removeMessages(MSG);
    }

    /**
     * 启动倒计时
     * 如果正处于倒计时状态那么不会有任何效果
     * 如果处于暂停状态下会按暂停时剩下的时间继续执行，
     * 如果需要从新开始计时，调用{@link #restart()}
     * 或先调用{@link #stop()} 然后调用 {@link #start()}
     */
    private synchronized void toStart() {
        if (mStatus == Status.RUNNING) {//如果正在倒计时就返回
            return;
        }

        long remainingTime = mTotalTime;
        if (mStatus == Status.PAUSE) {//如果是暂停状态
            if (mPauseRemainingTime > 0) {//暂停的剩余时间大于0
                remainingTime = mPauseRemainingTime;
            } else {//否者就认为结束
                initFinishStatus();
                onFinish();
                return;
            }
        }
        mStatus = Status.RUNNING;
        mStopTimeInFuture = SystemClock.elapsedRealtime() + remainingTime;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));

    }

    /**
     * 倒计时间隔回调
     *
     * @param remainingMillisecond 剩余毫秒数
     */
    protected abstract void onTick(long remainingMillisecond);

    /**
     * 倒计时结束回调
     */
    protected abstract void onFinish();

    private static class CountDownHandler extends Handler {
        private WeakReference<SnbCountDownTimer> weakReference;

        CountDownHandler(SnbCountDownTimer countDownTimer) {
            this.weakReference = new WeakReference<>(countDownTimer);
        }

        @Override
        public void handleMessage(Message msg) {
            SnbCountDownTimer timer = weakReference == null ? null : weakReference.get();
            if (timer == null) {
                return;
            }
            synchronized (CountDownHandler.class) {
                if (timer.mStatus == Status.RUNNING) {
                    //获得当前的时间点
                    long startTime = SystemClock.elapsedRealtime();
                    //还剩多少时间 = 截止时间- 当前时间
                    final long remainingTime = timer.mStopTimeInFuture - startTime;
                    if (remainingTime < 0) {
                        //小于0，结束并发送结束通知
                        timer.initFinishStatus();
                        timer.onFinish();
                        if (remainingTime < timer.mStopTimeInFuture) {
                            sendMessageDelayed(obtainMessage(MSG), remainingTime);
                        }
                    } else {
                        //否者将剩余时间发出
                        timer.onTick(remainingTime);
                        //需要延迟发送的时间 = 当前是按 + 间隔是按 - 当前时间
                        long delay = startTime + timer.mCountdownInterval - SystemClock.elapsedRealtime();
                        while (delay < 0) {
                            delay += timer.mCountdownInterval;
                        }
                        sendMessageDelayed(obtainMessage(MSG), delay);
                    }
                }
            }
        }
    }
}
