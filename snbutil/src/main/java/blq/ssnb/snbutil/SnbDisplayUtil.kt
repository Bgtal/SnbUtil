package blq.ssnb.snbutil

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期: 2017/4/10
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 3
 * 3:移除将结果转换为正数的功能
 * 描述:
 * 测量工具
 * 1.dp px sp 之间的转换
 * ================================================
</pre> *
 */
object SnbDisplayUtil {
    /**
     * 将dip、dp转换为px
     * @param context 上下文
     * @param dp 需要转化的dip长度
     * @return 返回px长度
     */
    @JvmStatic
    fun dp2Px(context: Context, dp: Float): Int {
        val scale: Float = getDisplayMetrics(context).density
        return (dp * scale + judgePosOrNeg(dp) * 0.5f).toInt()
    }

    /**
     * 将px转换为dip、dp
     * @param context 上下文
     * @param px 需要转化的px长度
     * @return 返回dip长度
     */
    @JvmStatic
    fun px2Dp(context: Context, px: Float): Int {
        val scale: Float = getDisplayMetrics(context).density
        return (px / scale + judgePosOrNeg(px) * 0.5f).toInt()
    }

    /**
     *
     * 将sp转换为px
     * @param context 上下文
     * @param sp 需要转化的px长度
     * @return 返回px长度
     */
    @JvmStatic
    fun sp2Px(context: Context, sp: Float): Int {
        val scale: Float = getDisplayMetrics(context).scaledDensity
        return (sp * scale + judgePosOrNeg(sp) * 0.5f).toInt()
    }

    /**
     * 将px转换为sp
     * @param context 上下文
     * @param px 需要转化的px长度
     * @return 返回sp长度
     */
    @JvmStatic
    fun px2Sp(context: Context, px: Float): Int {
        val scale: Float = getDisplayMetrics(context).scaledDensity
        return (px / scale + judgePosOrNeg(px) * 0.5f).toInt()
    }

    /**
     * 获取屏幕参数指标
     * @param context 上下文对象
     * @return 屏幕参数对象
     */
    @JvmStatic
    fun getDisplayMetrics(context: Context): DisplayMetrics {
        var metrics: DisplayMetrics = DisplayMetrics()
        if (context is Activity) {
            context.windowManager.defaultDisplay.getMetrics(metrics)
        } else {
            metrics = context.resources.displayMetrics
        }
        return metrics
    }

    /**
     * 判断正负
     * @param nub 传入的数字
     * @return {1,-1}
     */
    private fun judgePosOrNeg(nub: Float): Int {
        return if ((nub < 0.0f)) -1 else 1
    }
}
