package blq.ssnb.snbutil.rom

import android.content.Context
import android.content.Intent
import android.net.Uri
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
abstract class RomAdapter(
    private val mIRomBean: IRomBean,
    val romVersion: String = RomUtil.getRomVersion(mIRomBean),
) {

    val version: String get() = mIRomBean.version
    val romName: String get() = mIRomBean.romName

    protected open fun romError(msg: String) {
        Log.w("RomAdapter", "Error:$msg")
    }

    /**
     * 检查悬浮框权限
     */
    open fun checkFloatWindowPermission(context: Context?): Boolean {
        return Settings.canDrawOverlays(context)
    }

    /**
     * 判断是否有后台弹窗权限
     * 有该权限的Rom需要在对应的RomAdapter中自己实现，且默认返回false，
     * 无该权限的Rom不用实现，默认返回true
     */
    open fun canBackgroundPopup(context: Context?): Boolean {
        return true
    }

    open fun canBackgroundStartActivity(context: Context?): Boolean {
        return true
    }

    /**
     * 跳转到系统应用信息界面
     */
    open fun jumpToSystemAppInfo(context: Context?): Boolean {
        return context?.let {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.applicationContext.startActivity(intent)
            true
        } == true
    }

}