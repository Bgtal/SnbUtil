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
class MIRomAdapter : RomAdapter(Rom.MI) {

    override fun canBackgroundStartActivity(context: Context?): Boolean {
        try {
            val ops = context!!.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val op = 10021
            val method = ops.javaClass.getMethod(
                "checkOpNoThrow",
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                String::class.java
            )
            val result = method.invoke(ops, op, Process.myUid(), context.packageName) as Int
            return result == AppOpsManager.MODE_ALLOWED
        } catch (ignored: Exception) {
            romError("MI check BackgroundStartActivity error")
        }
        return false
    }
}