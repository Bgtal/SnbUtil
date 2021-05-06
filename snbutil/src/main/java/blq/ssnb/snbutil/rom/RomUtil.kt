package blq.ssnb.snbutil.rom

import android.os.Build
import blq.ssnb.snbutil.SnbLog
import blq.ssnb.snbutil.rom.adapter.CurrencyRom
import blq.ssnb.snbutil.rom.adapter.HuaweiRom
import blq.ssnb.snbutil.rom.adapter.MiuiRom
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2021/4/30
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
object RomUtil {

    val romAdapter: RomAdapter = when (getCurrentRom()) {
        Rom.HUAWEI -> HuaweiRom()
        Rom.MI -> MiuiRom()
        else -> {
            CurrencyRom()
        }
    }

    /**
     * 是否华为
     */
    val isHuaweiRom: Boolean get() = isRom(Rom.HUAWEI)

    /**
     * 是否小米
     */
    val isMiuiRom: Boolean get() = isRom(Rom.MI)

    /**
     * 是否是oppo
     */
    val isOppo: Boolean get() = isRom(Rom.OPPO)

    /**
     * 是否是Vivo
     */
    val isVivo: Boolean get() = isRom(Rom.VIVO)

    private fun isRom(rom: Rom): Boolean {
        val list = cmd("getprop ${rom.version}")
        var isRome = false
        for (s in list) {
            if (s.isNotEmpty()) {
                isRome = true
                break
            }
        }
        return isRome
    }

    fun getCurrentRom(): Rom {
        SnbLog.e(">>>>>获取当前rom")
        for (rom in enumValues<Rom>()) {
            if (isRom(rom)) {
                return rom
            }
        }
        return Rom.UNKNOW
    }

    //一个简易的CMD,暂时不做扩展
    private fun cmd(command: String): ArrayList<String> {
        val resultList = ArrayList<String>()

        var input: BufferedReader? = null
        try {
            //这里调用shell获取数据
            val p = Runtime.getRuntime().exec(command)
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            var line: String?
            while ((input.readLine().also { line = it }) != null) {
                //读取到返回的内容,加入到列表中
                line?.let { resultList.add(it) }
            }
            input.close()
            input = null

        } catch (e: Exception) {
            //如果报错了,就清空内容
            resultList.clear()
            return resultList
        } finally {
            try {
                input?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return resultList
    }

    /**
     * 获取build中的信息
     */
    fun getDeviceInfo() {
        val sb = StringBuffer()
        sb.append("主板： " + Build.BOARD + "\n");
        sb.append("系统启动程序版本号： " + Build.BOOTLOADER + "\n");
        sb.append("系统定制商：" + Build.BRAND + "\n");
        for ((index, e) in Build.SUPPORTED_ABIS.withIndex()) {
            sb.append("cpu指令集$index: $e\n");
        }
        sb.append("设置参数： " + Build.DEVICE + "\n");
        sb.append("显示屏参数：" + Build.DISPLAY + "\n");
        sb.append("无线电固件版本：" + Build.getRadioVersion() + "\n");
        sb.append("硬件识别码：" + Build.FINGERPRINT + "\n");
        sb.append("硬件名称：" + Build.HARDWARE + "\n");
        sb.append("HOST: " + Build.HOST + "\n");
        sb.append("修订版本列表：" + Build.ID + "\n");
        sb.append("硬件制造商：" + Build.MANUFACTURER + "\n");
        sb.append("版本：" + Build.MODEL + "\n");
        sb.append("硬件序列号：" + Build.SERIAL + "\n")
        sb.append("手机制造商：" + Build.PRODUCT + "\n");
        sb.append("描述Build的标签：" + Build.TAGS + "\n");
        sb.append("TIME: " + Build.TIME + "\n");
        sb.append("builder类型：" + Build.TYPE + "\n");
        sb.append("USER: " + Build.USER + "\n");
        SnbLog.e(">>>>>设备信息:\n$sb")
    }

}