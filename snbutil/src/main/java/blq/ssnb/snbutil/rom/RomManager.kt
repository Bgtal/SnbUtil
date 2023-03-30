package blq.ssnb.snbutil.rom

import android.content.Context

/**
 * ****************************************************************
 * 文件名称:RomManager.java
 * 作 者:SSNB
 * 创建时间:2023/3/24
 * 文件描述:
 * 注意事项:
 * 版权声明:Copyright (C) 2015-2025 杭州中焯信息技术股份有限公司
 * 修改历史:2023/3/24 1.00 初始版本
 * ****************************************************************
 */
class RomManager private constructor() {
    private var mRomAdapter: RomAdapter? = null
    var romAdapter: RomAdapter?
        get() = mRomAdapter
        set(romAdapter) {
            if (romAdapter == null) {
                throw NullPointerException("设置的 RomAdapter 不能为null")
            }
            mRomAdapter = romAdapter
        }

    val romName: String get() = mRomAdapter?.romName ?: "unknown"

    val romVersion: String get() = mRomAdapter?.romVersion ?: "unknown"

    /**
     * 判断是否可以在后台弹窗
     * @return 有该功能的手机一般默认为关:false
     */
    fun canBackgroundPopup(context: Context?): Boolean {
        return mRomAdapter?.canBackgroundPopup(context) == false
    }

    fun jumpToSystemAppInfo(context: Context?): Boolean {
        return mRomAdapter?.jumpToSystemAppInfo(context) == true
    }

    fun canBackgroundStartActivity(context: Context?): Boolean {
        return mRomAdapter?.canBackgroundStartActivity(context) == true
    }

    internal companion object {
        /**
         * 包安全模式，请使用[RomUtil.getCurrentRomManager]方法来获取单例
         * @return 单例对象
         */
        val instance = RomManager()
    }
}