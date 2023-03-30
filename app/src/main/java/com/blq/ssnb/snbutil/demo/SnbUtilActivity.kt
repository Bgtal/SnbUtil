package com.blq.ssnb.snbutil.demo

import android.view.View
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbToast
import blq.ssnb.snbutil.rom.Rom
import blq.ssnb.snbutil.rom.RomManager
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

        menu.add(MenuBean()
            .setMenuTitle("判断当前系统是否为某系统")
            .setMenuSubTitle("调用RomUtil.isXXXRom()可以判断是否为预设的几个rom，或者使用RomUtil.isRom()来判断预设外的其他系统")
            .setOnClickListener {
                val sb = StringBuffer()
                for (mRom in Rom.values()) {
                    sb.append("是否为${mRom.brand}系统:${RomUtil.isRom(mRom)}\n");
                }
                SnbToast.showShort(context,  sb.toString() )
            })

        menu.add(MenuBean()
            .setMenuTitle("获取当前系统")
            .setMenuSubTitle("使用 RomUtil.currentRomManager.romName 获取当前的Rom名称")
            .setOnClickListener {
                SnbToast.showSmart(activity, RomUtil.currentRomManager.romName)
            }
        )

        menu.add(
            MenuBean()
                .setMenuTitle("一些约定说明")
                .setMenuSubTitle(
                    "一些涉及到Rom特有的权限，例如canBackgroundPopup，" +
                            "在华为Rom中存在该权限，且默认情况下是关闭的，所以调用该方法，" +
                            "在失败的情况下返回false，而在其他Rom中不存在该权限，所以在判断的时候默认返回true"
                )
        )

        menu.add(MenuBean()
            .setMenuTitle("是否开启后台弹框权限")
            .setMenuSubTitle("调用RomUtil.currentRomManager.canBackgroundPopup来获取当前rom是否有该功能权限")
            .setOnClickListener {
                val canPop = RomUtil.currentRomManager.canBackgroundPopup(this)
                SnbToast.showSmart(activity, "是否有权限:$canPop")
            })

//        menu.add(MenuBean().setMenuTitle("B").setOnClickListener{
//            RomUtil.getRomAdapter()
//            RomUtil.mRomAdapter
//        })
        menu.add(MenuBean().setMenuTitle("获取设备信息")
            .setMenuSubTitle("使用 RomUtil.getDeviceInfo()，对Build中参数的拼接返回")
            .setOnClickListener {
            SnbToast.showSmart(activity, "当前设备信息:${RomUtil.getDeviceInfo()}")
        })


        return menu
    }
}