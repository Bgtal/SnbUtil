package blq.ssnb.snbutil.rom.adapter

import android.app.AppOpsManager
import android.content.Context
import android.os.Process
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
class HuaWeiRomAdapter : RomAdapter(Rom.HUAWEI) {

    override fun canBackgroundPopup(context: Context?): Boolean {
        try {
            val c = Class.forName("com.huawei.android.app.AppOpsManagerEx")
            val m = c.getDeclaredMethod(
                "checkHwOpNoThrow",
                AppOpsManager::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                String::class.java
            )
            var op = 100000
            op = c.getDeclaredField("HW_OP_CODE_POPUP_BACKGROUND_WINDOW").getInt(op)
            val checkResult = m.invoke(
                c.newInstance(),
                context!!.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager,
                op,
                Process.myUid(),
                context.packageName
            ) as Int
            return checkResult == AppOpsManager.MODE_ALLOWED

        } catch (ignored: java.lang.Exception) {
            romError("HuaWei check BackgroundPopup error")
        }
        return false;
    }

}