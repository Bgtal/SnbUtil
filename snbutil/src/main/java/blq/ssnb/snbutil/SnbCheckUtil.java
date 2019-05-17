package blq.ssnb.snbutil;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.util.List;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述: @hide
 * 检查工具
 * 1 判断当前线程是否在主线程
 * 2 判断当前网络是否为wifi ，数据流量
 * 3 判断某个服务是否在运行
 * 4 判断是否有权限
 * ================================================
 * </pre>
 */


public class SnbCheckUtil {

    private SnbCheckUtil() {
        throw new SnbIllegalInstantiationException();
    }

    /**
     * 是否主线程
     *
     * @return 判断当前调用方法是否在主线程
     */
    public static boolean isMainThread() {
        return Thread.currentThread().equals(Looper.getMainLooper().getThread());
    }

    /**
     * 当前网络是否为wifi
     *
     * @param context 上下文对象
     * @return true 连接 false 未连接或正在连接
     */
    public static boolean isWifiConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 当前网络是否为数据流量
     *
     * @param context 上下文对象
     * @return true 连接 false 未连接或正在连接
     */
    public static boolean isMobileConnected(Context context) {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE);
    }


    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            if (havePermission(context, permission.ACCESS_NETWORK_STATE)) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            } else {
                throw new RuntimeException("请检查是否有网络状态(android.permission.ACCESS_NETWORK_STATE)查看权限");
            }
        }
        return false;
    }


    /**
     * 检查连接状态
     *
     * @param context 上下文对象
     * @param type    类型{@link ConnectivityManager#TYPE_MOBILE}, {@link ConnectivityManager#TYPE_WIFI}
     * @return true 当前已经连接, false 未连接或在连接中
     */
    private static boolean isConnected(Context context, int type) {
        checkContext(context);
        if (havePermission(context, permission.ACCESS_NETWORK_STATE)) {
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
            return networkInfo != null
                    && networkInfo.isConnected()
                    && networkInfo.getType() == type;
        } else {
            throw new RuntimeException("请检查是否有网络状态(android.permission.ACCESS_NETWORK_STATE)查看权限");
        }
    }

    /**
     * 判断 context 是否为null
     *
     * @param context 待判断的context
     */
    static void checkContext(Context context) {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
    }

    /**
     * 判断服务是否存在
     *
     * @param context     上下文对象
     * @param serviceName 所需要服务的名字（完整名字 例如 blq.ssnb.mylib.service.MyService）
     * @return true 正在运行，false 未运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> mRunningTasks = activityManager.getRunningServices(1000);
            for (ActivityManager.RunningServiceInfo info : mRunningTasks) {
                if (info.service.getClassName().equals(serviceName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断所给的权限是否未授权
     *
     * @param context     上下文对象
     * @param permissions 需要判断的权限列表
     * @return true 权限丢失 false 权限存在
     */
    public static boolean isLostPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!havePermission(context, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否授权
     *
     * @param context     上下文对象
     * @param permissions 权限名称
     * @return true 有权限 false 没有授权
     * @see android.Manifest.permission
     */
    public static boolean havePermission(Context context, String... permissions) {
        checkContext(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //api 大于23
            //如果授权返回true 否者未授权
            for (String permission : permissions) {
                //如果未授权
                if (context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        } else {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param address  父目录
     * @param fileName 文件名
     * @return true 存在， false 不存在
     */
    public static boolean isFileExists(String address, String fileName) {
        File addressFile = new File(address);
        if (!addressFile.exists()) {
            return false;
        }
        return isFileExists(address + "/" + fileName);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true 存在，false 不存在
     */
    public static boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断屏幕是否为竖屏
     *
     * @param context 上下文对象
     * @return true 表示竖屏，默认返回竖屏
     */
    public static boolean isScreenPortrait(Context context) {
        checkContext(context);
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        boolean isPortrait = true;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            isPortrait = false;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isPortrait = true;
        }
        return isPortrait;
    }
}
