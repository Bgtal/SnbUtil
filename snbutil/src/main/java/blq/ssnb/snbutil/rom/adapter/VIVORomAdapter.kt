package blq.ssnb.snbutil.rom.adapter

import android.content.Context
import android.net.Uri
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
class VIVORomAdapter : RomAdapter(Rom.VIVO) {
    override fun checkFloatWindowPermission(context: Context?): Boolean {
        return false
    }

    override fun canBackgroundStartActivity(context: Context?): Boolean {
        var state = 1
        try {
            val uri =
                Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity")
            val selection = "pkgname = ?"
            val selectionArgs = arrayOf(context!!.packageName)
            val cursor = context.contentResolver.query(uri, null, selection, selectionArgs, null)
            if (cursor!!.moveToFirst()) {
                state = cursor.getInt(cursor.getColumnIndex("currentstate"))
            }
            cursor.close()
        } catch (ignored: Exception) {
            romError("VIVO check BackgroundStartActivity error")
        }
        return state == 0
    }
}