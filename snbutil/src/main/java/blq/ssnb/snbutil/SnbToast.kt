package blq.ssnb.snbutil

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import blq.ssnb.snbutil.SnbLog.w
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
 * 1.
 *
 * ================================================
</pre> *
 */
object SnbToast {
    /**
     * 弱应用的上下文对象
     */
    private var weakContext: WeakReference<Context?>? = null
    private var mMainToast: Toast? = null
    private var mThreadToast: Toast? = null

    /**
     * 传入Application 的上下文对象，需要在Application 中初始化
     *
     * @param context 继承Application的上下文对象
     */
    @JvmStatic
    fun init(context: Context?) {
        if (context is Application) {
            if (weakContext == null || weakContext!!.get() == null) {
                weakContext = WeakReference(context)
            }
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

    private fun showToast(context: Context?, msg: String?, isLongShow: Boolean) {
        if (msg == null || ("" == msg.trim { it <= ' ' })) {
            w("msg 为空，不显示Toast")
            return
        }
        if (SnbCheckUtil.isMainThread) {
            Toast.makeText(context, msg, if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare()
            }
            Toast.makeText(context, msg, if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
            Looper.loop()
        }
    }

    private fun showToast(msg: String?, isLongShow: Boolean) {
        if (msg == null || ("" == msg.trim { it <= ' ' })) {
            w("msg 为空，不显示Toast")
            return
        }
        if (SnbCheckUtil.isMainThread) {
            mMainToast?.cancel()
            mMainToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
            mThreadToast?.cancel()
            mMainToast?.setText(msg)
            mMainToast?.duration = if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            mMainToast?.show()
        } else {
            mMainToast?.cancel()
            Handler(Looper.getMainLooper()).post(Runnable {
                mThreadToast?.cancel()
                mThreadToast = Toast.makeText(mContext, msg, if (isLongShow) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
                mThreadToast?.show()
            })
        }
    }

    private val mContext: Context?
        get() {
            if (weakContext == null) {
                throw IllegalAccessError("请在Application中调用 SnbToast.init(Application)方法")
            }
            return weakContext!!.get()
        }
}