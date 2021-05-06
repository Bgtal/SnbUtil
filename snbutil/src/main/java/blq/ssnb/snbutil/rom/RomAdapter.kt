package blq.ssnb.snbutil.rom

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
abstract class RomAdapter(val rom : Rom) {
    /**
     * 检查悬浮框权限
     */
    abstract fun checkFloatWindowPermission(): Boolean

    fun openFloatWindowSetting(){

    }

}