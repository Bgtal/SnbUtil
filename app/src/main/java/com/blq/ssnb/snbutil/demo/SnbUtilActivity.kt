package com.blq.ssnb.snbutil.demo

import android.view.View
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbToast
import blq.ssnb.snbutil.rom.RomUtil

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
class SnbUtilActivity : SimpleMenuActivity() {
    override fun navigationTitle(): String = "Util类"

    override fun getMenuBeans(): MutableList<MenuBean> {
        val menu = ArrayList<MenuBean>()

        menu.add(MenuBean().setMenuTitle("判断当前系统是否为某系统").setMenuSubTitle("调用RomUtil.isXXXRom()可以判断是否是").setOnClickListener {
            SnbToast.showShort(context, "是否为华为系统:" + RomUtil.isHuaweiRom)
            SnbToast.showShort(context, "是否为小米系统:" + RomUtil.isMiuiRom)
        })

        menu.add(MenuBean().setMenuTitle("获取当前系统").setOnClickListener(object : View.OnClickListener {
            var i = 0
            override fun onClick(v: View?) {
                SnbToast.showSmart(activity, RomUtil.getCurrentRom().toString())
            }
        }))

        menu.add(MenuBean().setMenuTitle("B").setOnClickListener{
            RomUtil.romAdapter.checkFloatWindowPermission()
        })

//        menu.add(MenuBean().setMenuTitle("B").setOnClickListener{
//            RomUtil.getRomAdapter()
//            RomUtil.mRomAdapter
//        })
        menu.add(MenuBean().setMenuTitle("获取设备信息").setOnClickListener{
            RomUtil.getDeviceInfo()
        })


        return menu
    }
}