package blq.ssnb.snbutil.rom

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2021/5/5
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      手机系统枚举，用于适配不同机型
 * ================================================
 * </pre>
 */

enum class Rom(val brand: String, val romName: String, val version: String) {


    /**
     * 小米
     */
    MI("小米", "MIUI", "ro.miui.ui.version.name"),

    /**
     * 华为
     */
    HUAWEI("华为", "EMUI", "ro.build.version.emui"),

    /**
     * oppo
     */
    OPPO("OPPO", "OPPO", "ro.build.version.opporom"),

    /**
     * vivo
     */
    VIVO("vivo", "VIVO", "ro.vivo.os.version"),

    UNKNOW("未知", "UNKNOW", ""),

}