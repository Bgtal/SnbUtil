package blq.ssnb.snbutil.rom

import android.os.Build
import blq.ssnb.snbutil.SnbLog
import blq.ssnb.snbutil.rom.adapter.*
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

    val currentRomManager: RomManager
        get() {
            val manager: RomManager = RomManager.instance
            if (RomManager.instance.romAdapter == null) {
                var currentRom: Rom? = Rom.UNKNOW
                for (rom in Rom.values()) {
                    if (isRom(rom)) {
                        currentRom = rom
                        break
                    }
                }
                when (currentRom) {
                    Rom.MI -> manager.romAdapter = MIRomAdapter()
                    Rom.HUAWEI -> manager.romAdapter = HuaWeiRomAdapter()
                    Rom.OPPO -> manager.romAdapter = OPPORomAdapter()
                    Rom.VIVO -> manager.romAdapter = VIVORomAdapter()
                    else -> manager.romAdapter = CurrencyRomAdapter()
                }
            }
            return manager
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

    /**
     * 调用 getprop 命令来判断 手机的rom版本
     * @param rom 实现{@link IRomBean#version}版本判断的属性名称
     * @return true 对应的rom
     */
    public fun isRom(rom: IRomBean): Boolean {
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
    fun getDeviceInfo(): String {
        val sb = StringBuilder()
        sb.append("主板： ").append(Build.BOARD).append("\n")
        sb.append("系统启动程序版本号： ").append(Build.BOOTLOADER).append("\n")
        sb.append("系统定制商：").append(Build.BRAND).append("\n")
        val abis = Build.SUPPORTED_ABIS
        for (i in abis.indices) {
            sb.append("cpu指令集").append(i).append(": ").append(abis[i]).append("\n")
        }
        sb.append("设置参数： ").append(Build.DEVICE).append("\n")
        sb.append("显示屏参数：").append(Build.DISPLAY).append("\n")
        sb.append("无线电固件版本：").append(Build.getRadioVersion()).append("\n")
        sb.append("硬件识别码：").append(Build.FINGERPRINT).append("\n")
        sb.append("硬件名称：").append(Build.HARDWARE).append("\n")
        sb.append("HOST: ").append(Build.HOST).append("\n")
        sb.append("修订版本列表：").append(Build.ID).append("\n")
        sb.append("硬件制造商：").append(Build.MANUFACTURER).append("\n")
        sb.append("版本：").append(Build.MODEL).append("\n")
        sb.append("硬件序列号：").append(Build.SERIAL).append("\n")
        sb.append("手机制造商：").append(Build.PRODUCT).append("\n")
        sb.append("描述Build的标签：").append(Build.TAGS).append("\n")
        sb.append("TIME: ").append(Build.TIME).append("\n")
        sb.append("builder类型：").append(Build.TYPE).append("\n")
        sb.append("USER: ").append(Build.USER).append("\n")
        return sb.toString()
    }

}