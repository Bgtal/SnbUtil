package blq.ssnb.snbutil;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

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
 * </pre>
 */

public class SnbDisplayUtil {

    private SnbDisplayUtil(){
        throw new SnbIllegalInstantiationException();
    }
    /**
     * 将dip、dp转换为px
     * @param context 上下文
     * @param dp 需要转化的dip长度
     * @return 返回px长度
     */
    public static int dp2Px(Context context , float dp){
        final float scale = getDisplayMetrics(context).density;
        return (int)(dp * scale + judgePosOrNeg(dp)*0.5f);
    }

    /**
     * 将px转换为dip、dp
     * @param context 上下文
     * @param px 需要转化的px长度
     * @return 返回dip长度
     */
    public static int px2Dp(Context context , float px){
        final float scale = getDisplayMetrics(context).density;
        return (int)(px/scale+judgePosOrNeg(px)*0.5f);
    }

    /**
     *
     * 将sp转换为px
     * @param context 上下文
     * @param sp 需要转化的px长度
     * @return 返回px长度
     */
    public static int sp2Px(Context context , float sp){
        final float scale = getDisplayMetrics(context).scaledDensity;
        return (int) (sp*scale+judgePosOrNeg(sp)*0.5f);
    }

    /**
     * 将px转换为sp
     * @param context 上下文
     * @param px 需要转化的px长度
     * @return 返回sp长度
     */
    public static int px2Sp(Context context , float px){
        final float scale = getDisplayMetrics(context).scaledDensity;
        return (int) (px/scale+judgePosOrNeg(px)*0.5f);
    }

    /**
     * 获取屏幕参数指标
     * @param context 上下文对象
     * @return 屏幕参数对象
     */
    static DisplayMetrics getDisplayMetrics(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        if(context instanceof Activity){
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }else{
            metrics = context.getResources().getDisplayMetrics();
        }
        return metrics;
    }

    /**
     * 判断正负
     * @param nub 传入的数字
     * @return {1,-1}
     */
    private static int judgePosOrNeg(float nub){
        return (nub < 0.0F) ? -1: 1;
    }
}
