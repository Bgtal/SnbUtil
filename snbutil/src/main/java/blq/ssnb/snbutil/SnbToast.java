package blq.ssnb.snbutil;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.StringRes;
import android.widget.Toast;

import java.lang.ref.WeakReference;

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
 * </pre>
 */

public class SnbToast {


    /**
     * 弱应用的上下文对象
     */
    private static WeakReference<Context> weakContext;
    private static Toast mMainToast;
    private static Toast mThreadToast;

    private SnbToast() {
        throw new SnbIllegalInstantiationException();
    }

    /**
     * 传入Application 的上下文对象，需要在Application 中初始化
     *
     * @param context 继承Application的上下文对象
     */
    public static void init(Context context) {
        if (context instanceof Application) {
            if (weakContext == null || weakContext.get() == null) {
                weakContext = new WeakReference<>(context);
            }
        } else {
            throw new ClassCastException("context 必须是继承Application的上下文对象，否者会导致内存泄漏");
        }
    }

    /**
     * 短提示 连续多次调用只会显示最后一次内容
     *
     * @param msg StringID
     */
    public static void showShort(@StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showShort(sMsg);
    }

    /**
     * 长提示 连续多次调用只会显示最后一次内容
     *
     * @param msg StringID
     */
    public static void showLong(@StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showLong(sMsg);
    }

    /**
     * 自动判断是使用长提示还是短提示 连续多次调用只会显示最后一次内容
     *
     * @param msg StringID
     */
    public static void showSmart(@StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showSmart(sMsg);
    }

    /**
     * 短提示 连续多次调用只会显示最后一次内容
     *
     * @param msg 内容
     */
    public static void showShort(String msg) {
        showToast(msg, false);
    }

    /**
     * 长提示 连续多次调用只会显示最后一次内容
     *
     * @param msg 内容
     */
    public static void showLong(String msg) {
        showToast(msg, true);
    }

    /**
     * 自动判断是使用长提示还是短提示 连续多次调用只会显示最后一次内容
     *
     * @param msg 内容
     */
    public static void showSmart(String msg) {
        showToast(msg, msg.length() > 20);
    }

    /**
     * 短提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    public static void showShort(Context context, @StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showShort(context, sMsg);
    }

    /**
     * 长提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    public static void showLong(Context context, @StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showLong(context, sMsg);
    }

    /**
     * 自动判断是使用长提示还是短提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     StringID
     */
    public static void showSmart(Context context, @StringRes int msg) {
        String sMsg = getContext().getString(msg);
        showSmart(context, sMsg);

    }

    /**
     * 短提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    public static void showShort(Context context, String msg) {
        showToast(context, msg, false);
    }

    /**
     * 长提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    public static void showLong(Context context, String msg) {
        showToast(context, msg, true);
    }

    /**
     * 自动判断是使用长提示还是短提示 多次调用会按顺序依次显示完 主要用于比较重要的提示
     *
     * @param context 上下文对象
     * @param msg     内容
     */
    public static void showSmart(Context context, String msg) {
        showToast(context, msg, msg.length() > 20);
    }

    private static void showToast(Context context, String msg, boolean isLongShow) {
        if (msg == null || "".equals(msg.trim())) {
            SnbLog.w("msg 为空，不显示Toast");
            return;
        }
        if (SnbCheckUtil.isMainThread()) {
            Toast.makeText(context, msg, isLongShow ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
        } else {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            Toast.makeText(context, msg, isLongShow ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    private static void showToast(final String msg, final boolean isLongShow) {
        if (msg == null || "".equals(msg.trim())) {
            SnbLog.w("msg 为空，不显示Toast");
            return;
        }
        if (SnbCheckUtil.isMainThread()) {
            if (mMainToast != null) {
                mMainToast.cancel();
            }
            mMainToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
            if (mThreadToast != null) {
                mThreadToast.cancel();
            }
            mMainToast.setText(msg);
            mMainToast.setDuration(isLongShow ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            mMainToast.show();
        } else {
            if (mMainToast != null) {
                mMainToast.cancel();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (mThreadToast != null) {
                        mThreadToast.cancel();
                    }
                    mThreadToast = Toast.makeText(getContext(), msg, isLongShow ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                    mThreadToast.show();
                }
            });
        }
    }

    private static Context getContext() {
        if (weakContext == null) {
            throw new IllegalAccessError("请在Application中调用 SnbToast.init(Application)方法");
        }
        return weakContext.get();
    }
}
