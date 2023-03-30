package blq.ssnb.snbutil.rom

/**
 * ****************************************************************
 * 文件名称:IRomBean.java
 * 作 者:SSNB
 * 创建时间:2023/3/24
 * 文件描述: getVersion 用于 getprop 命令判断 手机rom的版本，根据不同手机厂商去设置这个值
 * 如果有新的机型要适配，实现该接口，调用[RomUtil.isRom]来判断
 * 注意事项:
 * 版权声明:Copyright (C) 2015-2025 杭州中焯信息技术股份有限公司
 * 修改历史:2023/3/24 1.00 初始版本
 * ****************************************************************
 */
interface IRomBean {
    val romName: String
    val version: String
}

/**
 * 预设一些常用的Rom
 */
enum class Rom(val brand: String,override val romName: String, override val version: String) : IRomBean {


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