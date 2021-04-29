package blq.ssnb.snbutil

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 1.获取屏幕的宽高
 * 2.获取状态栏高度
 * 3.（自定义）截屏
 * 屏幕获取宽高最好在[android.app.Activity.setContentView]之后调用，
 * 否者可能会出现宽高为0的状态
 * ================================================
</pre> *
 */
object SnbScreenUtil {
    private const val EMPTY = -1
    /**
     * 获得屏幕的宽度
     *
     * @param context 上下文对象
     * @return 屏幕宽度（px）
     */
    @Deprecated("由于存在底部虚拟键和分屏模式，传入的context类型不一样导致获取到的宽度不一样，所以放弃改用新的更加明确的方法")
    @JvmStatic
    fun getScreenWidth(context: Context?): Int {
        return context?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(context)
            return metrics.widthPixels
        } ?: EMPTY
    }

    /**
     * 获得当前app所在窗口的宽度
     * (例如横屏分屏状态下获取到即app所占的宽度)
     *
     * @param activity activity 对象
     * @return 返回activity所在屏幕的宽度
     */
    @JvmStatic
    fun getAppScreenWidth(activity: Activity?): Int {
        return activity?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(activity)
            return metrics.widthPixels
        } ?: EMPTY
    }

    /**
     * 获得屏幕的宽度不包含虚拟导航栏
     * 当不分屏的时候 获取的值与[.getAppScreenWidth]的值一样
     * 当横屏分屏的时候 获得的宽度是整个屏幕去除导航栏的宽度与[.getAppScreenWidth]获取的值不一样
     * 当没有虚拟导航栏的时候宽度就是整个屏幕的宽度 与[.getFullScreenWidth] 的值一样
     *
     * @param application application
     * @return 屏幕不包含虚拟导航栏的宽度
     */
    @JvmStatic
    fun getAvailableScreenWidth(application: Application?): Int {
        return application?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(application)
            return metrics.widthPixels
        } ?: EMPTY
    }

    /**
     * 获取完整的屏幕的宽度即手机屏幕大小
     *
     * @param context 上下文对象
     * @return 手机屏幕的宽度
     */
    @JvmStatic
    fun getFullScreenWidth(context: Context): Int {
        return getReleaseScreenSize(context).x
    }

    /**
     * 获得屏幕的高度(包括状态栏)
     * 如果进入的时候有底部导航栏,那么高度 = 屏幕高度-底部导航栏高度
     * 如果没有底部导航栏那么 高度= 屏幕高度
     *
     * @param context 上下方对象
     * @return 屏幕高度（px）
     */
    @Deprecated("")
    fun getScreenHeight(context: Context?): Int {
        return context?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(context)
            return metrics.heightPixels
        } ?: EMPTY
    }

    /**
     * 获得当前app所在屏幕的高度
     * 上下分屏状态下返回的值是当前app所在屏幕占的高度
     *
     * @param activity activity 对象
     * @return 返回 app 所在屏幕的高度
     */
    @JvmStatic
    fun getAppScreenHeight(activity: Activity?): Int {
        return activity?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(activity)
            return metrics.heightPixels
        } ?: EMPTY
    }

    /**
     * 获得屏幕的高度不包含虚拟导航栏
     * 当不分屏的时候 获取的值与[.getAppScreenHeight]的值一样
     * 当上下分屏的时候 获得的高度是整个屏幕去除导航栏的高度与[.getAppScreenHeight]获取的值不一样
     * 当没有虚拟导航栏的时候高度就是整个屏幕的宽度 与[.getFullScreenHeight] 的值一样
     *
     * @param application application
     * @return 屏幕不包含虚拟导航栏的高度
     */
    @JvmStatic
    fun getAvailableScreenHeight(application: Application?): Int {
        return application?.let {
            val metrics = SnbDisplayUtil.getDisplayMetrics(application)
            return metrics.heightPixels
        } ?: EMPTY
    }

    /**
     * 获取完整的屏幕高度
     *
     * @param context 上下文对象
     * @return 完整的屏幕高度，包括屏幕的导航栏和虚拟机
     */
    @JvmStatic
    fun getFullScreenHeight(context: Context): Int {
        return getReleaseScreenSize(context).y
    }

    private fun getReleaseScreenSize(context: Context): Point {
        val windowManager: WindowManager
        (if (context is Activity) {
            context.windowManager
        } else {
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }).also { windowManager = it }
        val outPoint = Point(EMPTY, EMPTY)
        val display = windowManager.defaultDisplay
        display.getRealSize(outPoint)
        return outPoint
    }

    private const val dimenClassName = "com.android.internal.R\$dimen"

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文对象
     * @return 返回状态栏高度，如果出错会返回-1
     */
    @JvmStatic
    fun getStatusHeight(context: Context): Int {
        var statusHeight = EMPTY

        val cls: Class<*>
        try {
            cls = Class.forName(dimenClassName)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Log.e("error", "无法找到类:$dimenClassName")
            return statusHeight
        }
        val obj: Any
        try {
            obj = cls.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
            Log.e("error", "实例化类" + dimenClassName + "错误")
            return statusHeight
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            Log.e("error", "实例化类" + dimenClassName + "错误")
            return statusHeight
        }
        val height: Int
        try {
            height = cls.getField("status_bar_height")[obj].toString().toInt()
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            Log.e("error", "获取status_bar_height的字段错误")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            Log.e("error", "获取status_bar_height的字段错误")
        }
        return statusHeight
    }

    /**
     * 获取底部导航栏的高度,竖屏时候为高度，横屏的时候为宽度
     *
     * @param context 上下文对象
     * @return 导航的高度(宽度)
     */
    @JvmStatic
    fun getNavigationHeight(context: Context): Int {
        var vh = EMPTY
        val isPortrait = SnbCheckUtil.isScreenPortrait(context)
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val dm = DisplayMetrics()
        try {
            val c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            vh = if (isPortrait) {
                dm.heightPixels - display.height
            } else {
                dm.widthPixels - display.width
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vh
    }

    /**
     * 默认截屏 会保留状态栏的高度，但不会获取到状态栏的内容,
     * 如果状态栏有背景色的话,即截取的状态栏为白色（或黑色）
     *
     * 截屏,当前activity左上角为view的坐标原点(包括状态栏)，
     * x向右为正，y向下为正
     *
     * @param activity activity对象
     * @param startX   截屏起始位置（距离左边）px
     * @param startY   截屏其实位置（距离上边）px
     * @param width    截屏宽度（大于0） px
     * @param height   截屏高度（大于0） px
     * @return 返回截屏后的Bitmap 如果宽或高  &lt;=0 的话会返回 null
     */
    @JvmStatic
    @JvmOverloads
    fun screenShot(activity: Activity,
                   startX: Int = 0, startY: Int = 0,
                   width: Int = SnbDisplayUtil.getDisplayMetrics(activity).widthPixels,
                   height: Int = SnbDisplayUtil.getDisplayMetrics(activity).heightPixels): Bitmap? {
        //获取顶层view
        val view = activity.window.decorView
        return screenShot(view, startX, startY, width, height)
    }

    /**
     * 截取某个view下的内容，当前view左上角为view的坐标原点，
     * x向右为正，y向下为正，如果View及他的子View没有设置背景色或其他颜色，默认会用黑色填充
     *
     * @param parentView 目标view
     * @param startX     截屏起始位置（距离左边）px 默认0
     * @param startY     截屏其实位置（距离上边）px 默认0
     * @param width      截屏宽度（大于0） px 默认目标View的宽度
     * @param height     截屏高度（大于0） px 默认目标View的高度
     * @return 返回截屏后的Bitmap 如果宽或高 &lt;=0 的话会返回 null
     */
    @JvmStatic
    @JvmOverloads
    fun screenShot(parentView: View, startX: Int = 0, startY: Int = 0, width: Int = parentView.width, height: Int = parentView.height): Bitmap? {
        if (width <= 0 || height <= 0) {
            return null
        }
        //获取顶层view
        parentView.buildDrawingCache()

        //允许当前窗口保存缓存信息
        parentView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(parentView.drawingCache, startX, startY, width, height)

        //清除缓存
        parentView.destroyDrawingCache()
        return bitmap
    }

}