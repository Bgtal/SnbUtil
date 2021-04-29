package com.blq.ssnb.snbutil.demo

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.snbutil.SnbCheckUtil
import blq.ssnb.snbutil.SnbCountDownTimer
import blq.ssnb.snbutil.SnbToast
import blq.ssnb.snbutil.constant.SnbTimeConstant
import com.blq.ssnb.snbutil.R

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/10
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class SnbCountDownTimerActivity : BaseActivity() {

    private var countDownTimer: SnbCountDownTimer? = null

    private lateinit var countDownInfoView: TextView
    private lateinit var countDownProgress: ProgressBar
    private lateinit var initBtn: View
    private lateinit var startPauseBtn: TextView
    private lateinit var restartBtn: View
    private lateinit var stopBtn: View


    override fun contentView(): Int = R.layout.activity_snb_count_down_timer
    override fun initView() {
        countDownInfoView = findViewById(R.id.tv_count_down_info)
        countDownProgress = findViewById(R.id.pb_count_down_progress)
        initBtn = findViewById(R.id.tv_init_count_down_btn)
        startPauseBtn = findViewById(R.id.tv_start_pause)
        restartBtn = findViewById(R.id.tv_restart_btn)
        stopBtn = findViewById(R.id.tv_stop_btn)
        SnbCheckUtil.isMainThread

    }

    override fun bindData() {
    }

    override fun bindEvent() {
        initBtn.setOnClickListener {
            if (countDownTimer != null) {
                countDownTimer!!.stop()
            }
            val totalTime = SnbTimeConstant.ONE_MINUTES
            countDownProgress.max = (totalTime / SnbTimeConstant.ONE_SECONDS).toInt()
            updateCountDownInfo((totalTime / SnbTimeConstant.ONE_SECONDS).toInt())

            countDownTimer = object : SnbCountDownTimer(totalTime, SnbTimeConstant.ONE_SECONDS) {
                override fun onFinish() {
                    updateCountDownInfo(0)
                }

                override fun onTick(remainingMillisecond: Long) {
                    updateCountDownInfo((remainingMillisecond / SnbTimeConstant.ONE_SECONDS).toInt())
                }
            }
        }
        startPauseBtn.setOnClickListener {
            if (countDownTimer != null) {
                if (countDownTimer!!.timerStatus == SnbCountDownTimer.Status.FINISH
                        || countDownTimer!!.timerStatus == SnbCountDownTimer.Status.PAUSE) {
                    //如果是结束状态或者暂停状态
                    countDownTimer!!.start()
                    startPauseBtn.text = "暂停倒计时"
                } else {
                    countDownTimer!!.pause()
                    startPauseBtn.text = "开始倒计时"
                }
            } else {
                SnbToast.showSmart(msg = "请先初始化")
            }
        }

        restartBtn.setOnClickListener {
            if (countDownTimer != null) {
                countDownTimer!!.restart()
            } else {
                SnbToast.showSmart(msg = "请先初始化")
            }
        }

        stopBtn.setOnClickListener {
            if (countDownTimer != null) {//如果倒计时工具不为空
                if (countDownTimer!!.timerStatus != SnbCountDownTimer.Status.FINISH) {
                    countDownTimer!!.stop()
                }
            } else {
                SnbToast.showSmart(msg = "请先初始化")
            }
        }
    }


    fun updateCountDownInfo(limitTime: Int) {
        countDownInfoView.text = "倒计时:$limitTime"
        countDownProgress.progress = limitTime
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.stop()
    }

}