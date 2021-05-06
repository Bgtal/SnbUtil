package blq.ssnb.snbutil.rom.adapter

import android.app.Activity
import android.provider.Settings
import blq.ssnb.snbutil.rom.Rom
import blq.ssnb.snbutil.rom.RomAdapter

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
class HuaweiRom : RomAdapter(Rom.HUAWEI) {
    override fun checkFloatWindowPermission(): Boolean {
        val Con = Activity()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(Con)
        }
        return false;
    }


}