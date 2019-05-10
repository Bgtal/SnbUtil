package blq.ssnb.snbutil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;


/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *     1.获取屏幕的宽高
 *     2.获取状态栏高度
 *     3.（自定义）截屏
 * 屏幕获取宽高最好在{@link android.app.Activity#setContentView}之后调用，
 * 否者可能会出现宽高为0的状态
 * ================================================
 * </pre>
 */


public class SnbScreenUtil {

    private SnbScreenUtil() {
        throw new SnbIllegalInstantiationException();
    }

    /**
     * 获得屏幕的宽度
     *
     * @param context 上下文对象
     * @return 屏幕宽度（px）
     * @deprecated 由于存在底部虚拟键和分屏模式，传入的context类型不一样导致获取到的宽度不一样，所以放弃改用新的更加明确的方法
     */
    @Deprecated
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(context);
        return metrics.widthPixels;
    }

    /**
     * 获得当前app所在窗口的宽度
     * (例如横屏分屏状态下获取到即app所占的宽度)
     *
     * @param activity activity 对象
     * @return 返回activity所在屏幕的宽度
     */
    public static int getAppScreenWidth(Activity activity) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(activity);
        return metrics.widthPixels;
    }

    /**
     * 获得屏幕的宽度不包含虚拟导航栏
     * 当不分屏的时候 获取的值与{@link #getAppScreenWidth(Activity)}的值一样
     * 当横屏分屏的时候 获得的宽度是整个屏幕去除导航栏的宽度与{@link #getAppScreenWidth(Activity)}获取的值不一样
     * 当没有虚拟导航栏的时候宽度就是整个屏幕的宽度 与{@link #getFullScreenWidth(Context)} 的值一样
     *
     * @param application application
     * @return 屏幕不包含虚拟导航栏的宽度
     */
    public static int getAvailableScreenWidth(Application application) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(application);
        return metrics.widthPixels;
    }

    /**
     * 获取完整的屏幕的宽度即手机屏幕大小
     *
     * @param context 上下文对象
     * @return 手机屏幕的宽度
     */
    public static int getFullScreenWidth(Context context) {
        return getReleaseScreenSize(context).x;
    }

    /**
     * 获得屏幕的高度(包括状态栏)
     * 如果进入的时候有底部导航栏,那么高度 = 屏幕高度-底部导航栏高度
     * 如果没有底部导航栏那么 高度= 屏幕高度
     *
     * @param context 上下方对象
     * @return 屏幕高度（px）
     */
    @Deprecated
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(context);
        return metrics.heightPixels;
    }

    /**
     * 获得当前app所在屏幕的高度
     * 上下分屏状态下返回的值是当前app所在屏幕占的高度
     *
     * @param activity activity 对象
     * @return 返回 app 所在屏幕的高度
     */
    public static int getAppScreenHeight(Activity activity) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(activity);
        return metrics.heightPixels;
    }

    /**
     * 获得屏幕的高度不包含虚拟导航栏
     * 当不分屏的时候 获取的值与{@link #getAppScreenHeight(Activity)}的值一样
     * 当上下分屏的时候 获得的高度是整个屏幕去除导航栏的高度与{@link #getAppScreenHeight(Activity)}获取的值不一样
     * 当没有虚拟导航栏的时候高度就是整个屏幕的宽度 与{@link #getFullScreenHeight(Context)} 的值一样
     *
     * @param application application
     * @return 屏幕不包含虚拟导航栏的高度
     */
    public static int getAvailableScreenHeight(Application application) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(application);
        return metrics.heightPixels;
    }

    /**
     * 获取完整的屏幕高度
     *
     * @param context 上下文对象
     * @return 完整的屏幕高度，包括屏幕的导航栏和虚拟机
     */
    public static int getFullScreenHeight(Context context) {
        return getReleaseScreenSize(context).y;
    }

    private static Point getReleaseScreenSize(Context context) {
        WindowManager windowManager;
        if (context instanceof Activity) {
            windowManager = ((Activity) context).getWindowManager();
        } else {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        Point outPoint = new Point(-1, -1);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();

            display.getRealSize(outPoint);
            return outPoint;
        }
        return outPoint;
    }

    final private static String dimenClassName = "com.android.internal.R$dimen";

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文对象
     * @return 返回状态栏高度，如果出错会返回-1
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        Class<?> cls;
        try {
            cls = Class.forName(dimenClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("error", "无法找到类:" + dimenClassName);
            return statusHeight;
        }
        Object object;
        try {
            object = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e("error", "实例化类" + dimenClassName + "错误");
            return statusHeight;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("error", "实例化类" + dimenClassName + "错误");
            return statusHeight;
        }
        int height;
        try {
            height = Integer.parseInt(cls.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("error", "获取status_bar_height的字段错误");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.e("error", "获取status_bar_height的字段错误");
        }
        return statusHeight;
    }

    /**
     * 获取底部导航栏的高度,竖屏时候为高度，横屏的时候为宽度
     *
     * @param context 上下文对象
     * @return 导航的高度(宽度)
     */
    public static int getNavigationHeight(Context context) {
        int vh = -1;
        boolean isPortrait = SnbCheckUtil.isScreenPortrait(context);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            if (isPortrait) {
                vh = dm.heightPixels - display.getHeight();
            } else {
                vh = dm.widthPixels - display.getWidth();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /**
     * 截屏 会保留状态栏的高度，但不会获取到状态栏的内容,
     * 如果状态栏有背景色的话,即截取的状态栏为白色（或黑色）
     *
     * @param activity activity
     * @return 返回截图
     */
    public static Bitmap screenShot(Activity activity) {
        DisplayMetrics metrics = SnbDisplayUtil.getDisplayMetrics(activity);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return screenShot(activity, 0, 0, width, height);
    }

    /**
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
    public static Bitmap screenShot(Activity activity, int startX, int startY, int width, int height) {
        //获取顶层view
        View view = activity.getWindow().getDecorView();
        return screenShot(view, startX, startY, width, height);
    }

    /**
     * 截取某个View的所有内容，如果View及他的子View没有设置背景色或其他颜色，默认会用黑色填充
     *
     * @param contentView 待截取的view
     * @return 返回view的截图内容 如果view的宽或高  &lt;=0 的话会返回 null
     */
    public static Bitmap screenShot(View contentView) {
        return screenShot(contentView, 0, 0, contentView.getWidth(), contentView.getHeight());
    }

    /**
     * 截取某个view下的内容，当前view左上角为view的坐标原点，
     * x向右为正，y向下为正，如果View及他的子View没有设置背景色或其他颜色，默认会用黑色填充
     *
     * @param parentView 目标view
     * @param startX     截屏起始位置（距离左边）px
     * @param startY     截屏其实位置（距离上边）px
     * @param width      截屏宽度（大于0） px
     * @param height     截屏高度（大于0） px
     * @return 返回截屏后的Bitmap 如果宽或高 &lt;=0 的话会返回 null
     */
    public static Bitmap screenShot(View parentView, int startX, int startY, int width, int height) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        //获取顶层view
        parentView.buildDrawingCache();

        //允许当前窗口保存缓存信息
        parentView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(parentView.getDrawingCache(), startX, startY, width, height);

        //清除缓存
        parentView.destroyDrawingCache();

        return bitmap;
    }
}
