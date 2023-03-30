package blq.ssnb.snbutil

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.NotificationManagerCompat
import blq.ssnb.snbutil.SnbLog.w
import blq.ssnb.snbutil.rom.RomUtil
import blq.ssnb.snbutil.toast.Toast9
import java.lang.ref.WeakReference

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 2
 * 1.对Toast的封装支持位置修改等，是否取消前一个toast的功能
 * 2.删除对位置修改的功能，提供了方法可以使用提供的context显示toast 并且该toast无法被取消只能等到时间结束，用于比较重要的提示的展示
 * 描述:
 * 对Toast的封装,使用前需要在Application 中调用SnbToast.init(Context)方法;
 *
 * @note Android 9 对系统toast 做了处理，会导致
 * 1.连续调用系统Toast，将前面的toast顶掉
 * 2.关闭通知权限导致 toast不显示
 *
 *
 * ================================================
</pre> *
 */
object SnbToast : Application.ActivityLifecycleCallbacks {

    private var mMainToast: Toast? = null
    private var mThreadToast: Toast? = null
    private var mMainToast9: Toast9? = null
    private var mThreadToast9: Toast9? = null

    /**
     * 传入Application 的上下文对象，需要在Application 中初始化
     *
     * @param context 继承Application的上下文对象
     */
    @JvmStatic
    fun init(context: Context?) {
        if (context is Application) {
            context.registerActivityLifecycleCallbacks(this)
        } else {
            throw ClassCastException("context 必须是继承Application的上下文对象，否者会导致内存泄漏")
        }
    }

    /**
     * 短提示 context 为 null 时 会连续多次调用只会显示最后一次内容
     * 短提示 context 不为 null时 会多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    @JvmStatic
    @JvmOverloads
    fun showShort(context: Context? = null, @StringRes msg: Int) {
        if (context != null) {
            val sMsg = context.getString(msg)
            showShort(context, sMsg)
        } else {
            val sMsg: String? = mContext?.getString(msg)
            showShort(msg = sMsg)
        }
    }

    /**
     * 短提示 context 为 null 时 会连续多次调用只会显示最后一次内容
     * 短提示 context 不为 null时 会多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    @JvmStatic
    @JvmOverloads
    fun showShort(context: Context? = null, msg: String?) {
        if (context == null) {
            showToast(msg, false)
        } else {
            showToast(context, msg, false)
        }
    }


    /**
     * 长提示 context 为 null 时 连续多次调用只会显示最后一次内容
     * 长提示 context 不为 null时 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    @JvmStatic
    @JvmOverloads
    fun showLong(context: Context? = null, @StringRes msg: Int) {
        if (context != null) {
            val sMsg = context.getString(msg)
            showLong(context, sMsg)
        } else {
            val sMsg = mContext?.getString(msg)
            showLong(msg = sMsg)
        }
    }

    /**
     * 长提示 context 为 null 时 连续多次调用只会显示最后一次内容
     * 长提示 context 不为 null时 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    @JvmStatic
    @JvmOverloads
    fun showLong(context: Context? = null, msg: String?) {
        if (context != null) {
            showToast(context, msg, true)
        } else {
            showToast(msg, true)
        }
    }

    /**
     * 自动判断是使用长提示还是短提示
     * 当 context 为 null 时 连续多次调用只会显示最后一次内容
     * 当 context 不为 null 时 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    @JvmStatic
    @JvmOverloads
    fun showSmart(context: Context? = null, @StringRes msg: Int) {
        if (context != null) {
            val sMsg = context.getString(msg)
            showSmart(context, sMsg)
        } else {
            val sMsg = mContext?.getString(msg)
            showSmart(msg = sMsg)
        }
    }

    /**
     * 自动判断是使用长提示还是短提示
     * 当 context 为 null 时 连续多次调用只会显示最后一次内容
     * 当 context 不为 null 时 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    @JvmStatic
    @JvmOverloads
    fun showSmart(context: Context? = null, msg: String?) {
        if (context != null) {
            showToast(context, msg, msg?.let { it.length > 20 } ?: false)
        } else {
            showToast(msg, msg?.let { it.length > 20 } ?: false)
        }
    }

    private fun showToast(context: Context, msg: String?, isLongShow: Boolean) {
        if (msg == null || ("" == msg.trim { it <= ' ' })) {
            w("msg 为空，不显示Toast")
            return
        }
        if (SnbCheckUtil.isMainThread) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()
                && RomUtil.currentRomManager.canBackgroundPopup(context)
            ) {
                Toast.makeText(
                    context,
                    msg,
                    if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast9.makeText(
                    context,
                    msg,
                    if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                Toast.makeText(
                    context,
                    msg,
                    if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast9.makeText(
                    context,
                    msg,
                    if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                ).show()
            }
            Looper.loop()
        }
    }

    private fun showToast(msg: String?, isLongShow: Boolean) {
        if (msg == null || ("" == msg.trim { it <= ' ' })) {
            w("msg 为空，不显示Toast")
            return
        }
        if (SnbCheckUtil.isMainThread) {
            cancelMain()
            cancelThread()
            mContext?.let {
                if (NotificationManagerCompat.from(it).areNotificationsEnabled()
                    && RomUtil.currentRomManager.canBackgroundPopup(it)
                ) {
                    mMainToast = Toast.makeText(it, msg, Toast.LENGTH_SHORT)
                    mMainToast?.show()
                } else {
                    mMainToast9 = Toast9.makeText(
                        it,
                        msg,
                        if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                    )
                    mMainToast9?.show()
                }
            }

        } else {
            cancelMain()
            Handler(Looper.getMainLooper()).post(Runnable {
                cancelThread()
                mContext?.let {
                    if (NotificationManagerCompat.from(it).areNotificationsEnabled()) {
                        mThreadToast = Toast.makeText(
                            mContext,
                            msg,
                            if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
                        )
                        mThreadToast?.show()
                    } else {
                        mThreadToast9 = Toast9.makeText(it, msg, Toast.LENGTH_SHORT)
                        mThreadToast9?.show()
                    }
                }
            })
        }
    }

    private fun cancelMain() {
        mMainToast?.cancel()
        mMainToast9?.cancel()

    }

    private fun cancelThread() {
        mThreadToast?.cancel()
        mThreadToast9?.cancel()
    }

    private val mContext: Context?
        get() {
            return currentActivity?.get()
        }

    private var currentActivity: WeakReference<Context?>? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity != currentActivity?.get()) {
            currentActivity?.clear()
            currentActivity = WeakReference(activity)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity != currentActivity?.get()) {
            currentActivity?.clear()
            currentActivity = WeakReference(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity != currentActivity?.get()) {
            currentActivity?.clear()
            currentActivity = WeakReference(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if(activity == currentActivity?.get()){
            currentActivity?.clear()
        }
    }
}