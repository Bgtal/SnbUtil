package blq.ssnb.snbutil.rom

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2021/5/6
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
abstract class RomAdapter(private val mIRomBean: IRomBean) {

    val version: String get() = mIRomBean.version
    val romName: String get() = mIRomBean.romName

    protected open fun romError(msg: String) {
        Log.w("RomAdapter", "Error:$msg")
    }

    /**
     * 检查悬浮框权限
     */
    open fun checkFloatWindowPermission(context: Context?): Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context)
        }
        return true
    }

    /**
     * 判断是否有后台弹窗权限
     * 有该权限的Rom需要在对应的RomAdapter中自己实现，且默认返回false，
     * 无该权限的Rom不用实现，默认返回true
     */
    open fun canBackgroundPopup(context: Context?): Boolean {
        return true
    }

    open fun canBackgroundStartActivity(context: Context?):Boolean{
        return true
    }

}