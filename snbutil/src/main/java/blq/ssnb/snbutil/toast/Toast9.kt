package blq.ssnb.snbutil.toast

import android.content.Context
import android.content.res.Resources
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import java.lang.ref.WeakReference
import java.util.*

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2021/5/6
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      应对android9的吐司显示,达到和系统显示的一样
 *      这里参考了
 *      https://github.com/Blincheng/EToast2
 *      关于toast在android 9 的适配,给了我一些思路，然后自己进行了扩展
 * ================================================
 * </pre>
 */
internal class Toast9 constructor(context: Context, text: CharSequence, duration: Int) {

    private val manager: WindowManager = context.getSystemService(WindowManager::class.java)
    private var contentView: WeakReference<View?>? = null
    private var params: WindowManager.LayoutParams? = null
    private var timer: Timer? = null
    private var durationTime: Long = 2000

    var state = STATE_NORMAL


    companion object {

        private var toastHandler: ToastHandler? = null
        internal const val STATE_NORMAL = 0//等待
        internal const val STATE_WAITING = 1//等待
        internal const val STATE_SHOWING = 2//
        internal const val STATE_CANCEL = 3

        fun makeText(context: Context, text: CharSequence, duration: Int): Toast9 {
            return Toast9(context, text, duration)
        }

        fun makeText(context: Context, resId: Int, duration: Int): Toast9 {
            return makeText(context, context.getText(resId).toString(), duration)
        }
    }


    init {
        durationTime = if (duration == Toast.LENGTH_SHORT) {
            2000L
        } else {
            3500L
        }
        //生成一个Toast，主要是从系统的toast中拿到View对象，之后用于加入到 WindowManager 中
        val mToast = Toast.makeText(context, text, duration)
        contentView = WeakReference(mToast?.view)

        /**
         * 因为 Toast.getWindowParams 调用不到，直接查到了生成的地方
         * 这里参考了 {ToastPresenter#createLayoutParams}方法生成系统的toast LayoutParams
         * 同时y位置和gravity位置，通过获取系统的位置来设置，可以达到显示一致
         */
        params = WindowManager.LayoutParams()
        params?.apply {
            height = WindowManager.LayoutParams.WRAP_CONTENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            format = PixelFormat.TRANSLUCENT
            windowAnimations = android.R.style.Animation_Toast
            title = "Toast"
            flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            y = context.resources.getDimensionPixelSize(
                Resources.getSystem().getIdentifier("toast_y_offset", "dimen", "android")
            )
            gravity = context.resources.getInteger(
                Resources.getSystem()
                    .getIdentifier("config_toastDefaultGravity", "integer", "android")
            )
        }

        if (toastHandler == null) {
            toastHandler = ToastHandler(context.mainLooper)
        }
    }

    fun show() {
        toastHandler?.sendToastShow(this)
    }

    internal fun realShow() {
        state = STATE_SHOWING
        timer?.cancel()

        val existView = contentView?.get()?.let {
            manager.addView(it, params)
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    toastHandler?.sendToastCancel(this@Toast9)
                }
            }, durationTime)
            it
        }
        if (existView == null) {
            cancel()
        }
    }

    fun cancel() {
        toastHandler?.sendToastCancel(this)
    }

    internal fun realCancel() {
        state = STATE_CANCEL
        try {
            manager.removeView(contentView?.get())
        } catch (e: IllegalArgumentException) {
        }
        timer?.cancel()
        timer = null
        contentView = null
    }
}