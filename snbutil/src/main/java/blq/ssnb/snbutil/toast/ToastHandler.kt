package blq.ssnb.snbutil.toast

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.MessageQueue
import blq.ssnb.snbutil.SnbLog
import java.util.*
import kotlin.collections.ArrayDeque

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2021/5/6
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 为了实现和系统的差不多的效果，这里采用了队列的方式将toast进行管理
 * 这里简单的写了一下，逻辑上应该没有问题，感觉可能有bug，实际使用过程中发现了再说
 * ================================================
 * </pre>
 */
internal class ToastHandler constructor(lopper: Looper) : Handler(lopper) {

    companion object {
        const val MSG_WHAT_SHOW_TOAST = 1;
        const val MSG_WHAT_CANCEL_TOAST = 2;
        val toastQueue: LinkedList<Toast9> = LinkedList()
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val toast: Toast9 = msg.obj as Toast9
        when (msg.what) {
            MSG_WHAT_SHOW_TOAST -> {
                showToast(toast)
            }
            MSG_WHAT_CANCEL_TOAST -> {
                val isN = cancelToast(toast)
                if (isN) {
                    toastQueue.first.realShow()
                }
            }
        }
    }

    fun sendToastShow(toast: Toast9) {
        val msg = Message.obtain()
        msg.what = MSG_WHAT_SHOW_TOAST
        msg.obj = toast
        sendMessage(msg)
    }

    fun sendToastCancel(toast: Toast9) {
        val msg = Message.obtain()
        msg.what = MSG_WHAT_CANCEL_TOAST
        msg.obj = toast
        sendMessage(msg)
    }

    private fun showToast(toast: Toast9) {
        toastQueue.add(toast)
        if (toastQueue.size == 1) {
            toast.realShow()
        }
    }

    private fun cancelToast(toast: Toast9): Boolean {
        //找到Toast
        val fToast = toastQueue.find { it == toast }
        when (fToast?.state) {
            //如果是normal表示有问题了，直接移除掉,如果是等待中或者已经结束了，那就先移除掉再说
            Toast9.STATE_NORMAL, Toast9.STATE_WAITING, Toast9.STATE_CANCEL -> toastQueue.remove(fToast)
            //如果是显示中那么先取消的再说
            Toast9.STATE_SHOWING -> {
                fToast.realCancel()
                toastQueue.remove(fToast)
                return toastQueue.isNotEmpty()
            }
        }
        return false
    }
}