package com.blq.ssnb.snbutil

import blq.ssnb.baseconfigure.AbsApplication
import blq.ssnb.baseconfigure.LogManager
import blq.ssnb.snbutil.SnbLog
import blq.ssnb.snbutil.SnbToast

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/11
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
</pre> *
 */
class MApplication : AbsApplication() {

    override fun initSnb() {
        SnbToast.init(this)
        SnbLog.getGlobalBuilder().isOpen(true)
        LogManager.openLog(false, false)
    }

    override fun initBugly() {

    }

    override fun initNetWork() {

    }

    override fun initSingle() {

    }
}