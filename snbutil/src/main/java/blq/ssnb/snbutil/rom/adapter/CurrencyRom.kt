package blq.ssnb.snbutil.rom.adapter

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
 *      通用rom
 * ================================================
 * </pre>
 */
class CurrencyRom() : RomAdapter(Rom.UNKNOW){
    override fun checkFloatWindowPermission(): Boolean {
        return false
    }
}