package blq.ssnb.snbutil

import android.Manifest
import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import java.io.File

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
</pre> *
 */
object SnbCheckUtil {
    /**
     * 是否主线程
     *
     * @return 判断当前调用方法是否在主线程
     */
    @JvmStatic
    val isMainThread: Boolean
        get() {
            return (Thread.currentThread() == Looper.getMainLooper().thread)
        }

    /**
     * 当前网络是否为wifi
     *
     * @param context 上下文对象
     * @return true 连接 false 未连接或正在连接
     */
    @JvmStatic
    fun isWifiConnected(context: Context): Boolean {
        return isConnected(context, ConnectivityManager.TYPE_WIFI)
    }

    /**
     * 当前网络是否为数据流量
     *
     * @param context 上下文对象
     * @return true 连接 false 未连接或正在连接
     */
    @JvmStatic
    fun isMobileConnected(context: Context): Boolean {
        return isConnected(context, ConnectivityManager.TYPE_MOBILE)
    }

    /**
     * 网络是否可用
     * 需要添加权限 android.permission.ACCESS_NETWORK_STATE
     */
    @JvmStatic
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            if (havePermission(context, permission.ACCESS_NETWORK_STATE)) {
                val mConnectivityManager: ConnectivityManager = context
                        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val mNetworkInfo: NetworkInfo? = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable()
                }
            } else {
                throw RuntimeException("请检查是否有网络状态(android.permission.ACCESS_NETWORK_STATE)查看权限")
            }
        }
        return false
    }

    /**
     * 检查连接状态
     *
     * @param context 上下文对象
     * @param type    类型[ConnectivityManager.TYPE_MOBILE], [ConnectivityManager.TYPE_WIFI]
     * @return true 当前已经连接, false 未连接或在连接中
     */
    @JvmStatic
    private fun isConnected(context: Context, type: Int): Boolean {
        checkContext(context)
        if (havePermission(context, permission.ACCESS_NETWORK_STATE)) {
            val cManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val networkInfo: NetworkInfo? = cManager.getActiveNetworkInfo()
            return ((networkInfo != null
                    ) && networkInfo.isConnected()
                    && (networkInfo.getType() == type))
        } else {
            throw RuntimeException("请检查是否有网络状态(android.permission.ACCESS_NETWORK_STATE)查看权限")
        }
    }

    /**
     * 判断 context 是否为null
     *
     * @param context 待判断的context
     */
    @JvmStatic
    fun checkContext(context: Context?) {
        if (context == null) {
            throw NullPointerException("context is null")
        }
    }

    /**
     * 判断服务是否存在
     *
     * @param context     上下文对象
     * @param serviceName 所需要服务的名字（完整名字 例如 blq.ssnb.mylib.service.MyService）
     * @return true 正在运行，false 未运行
     */
    @JvmStatic
    fun isServiceRunning(context: Context, serviceName: String): Boolean {
        try {
            val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val mRunningTasks: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(1000)
            for (info: ActivityManager.RunningServiceInfo in mRunningTasks) {
                if ((info.service.className == serviceName)) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 判断所给的权限是否未授权
     *
     * @param context     上下文对象
     * @param permissions 需要判断的权限列表
     * @return true 权限丢失 false 权限存在
     */
    @JvmStatic
    fun isLostPermission(context: Context, vararg permissions: String?): Boolean {
        for (permission: String? in permissions) {
            if (!havePermission(context, permission)) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否授权
     *
     * @param context     上下文对象
     * @param permissions 权限名称
     * @return true 有权限 false 没有授权
     * @see android.Manifest.permission
     */
    @JvmStatic
    fun havePermission(context: Context, vararg permissions: String?): Boolean {
        checkContext(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //api 大于23
            //如果授权返回true 否者未授权
            for (permission: String? in permissions) {
                //如果未授权
                if ((permission?.let { context.checkSelfPermission(it) }
                                == PackageManager.PERMISSION_DENIED)) {
                    return false
                }
            }
        } else {
            for (permission: String? in permissions) {
                if ((permission?.let { ContextCompat.checkSelfPermission(context, it) }
                                == PackageManager.PERMISSION_DENIED)) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 判断文件是否存在
     *
     * @param address  父目录
     * @param fileName 文件名
     * @return true 存在， false 不存在
     */
    @JvmStatic
    fun isFileExists(address: String, fileName: String): Boolean {
        val addressFile: File = File(address)
        if (!addressFile.exists()) {
            return false
        }
        return isFileExists("$address/$fileName")
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true 存在，false 不存在
     */
    @JvmStatic
    fun isFileExists(filePath: String?): Boolean {
        val file: File = File(filePath)
        return file.exists()
    }

    /**
     * 判断屏幕是否为竖屏
     *
     * @param context 上下文对象
     * @return true 表示竖屏，默认返回竖屏
     */
    @JvmStatic
    fun isScreenPortrait(context: Context): Boolean {
        checkContext(context)
        val mConfiguration: Configuration = context.resources.configuration //获取设置的配置信息
        val ori: Int = mConfiguration.orientation //获取屏幕方向
        var isPortrait = true
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            isPortrait = false
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isPortrait = true
        }
        return isPortrait
    }
}
