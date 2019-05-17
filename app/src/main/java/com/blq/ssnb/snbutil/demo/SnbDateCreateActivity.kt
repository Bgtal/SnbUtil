package com.blq.ssnb.snbutil.demo

import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/10
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class SnbDateCreateActivity : SimpleMenuActivity() {
    override fun getMenuBeans(): MutableList<MenuBean> {
        val menuList = ArrayList<MenuBean>()

        menuList.add(MenuBean().setMenuSubTitle("创建列表对象,方便写demo的时候填充列表,将前后端分离,配合mvp食用效果更佳"))

        menuList.add(MenuBean().setMenuTitle("同步创建数据")
                .setMenuSubTitle("调用 SnbDateCreateUtil.createListData()同步创建数据"))

        menuList.add(MenuBean().setMenuTitle("异步创建数据")
                .setMenuSubTitle("调用 SnbDateCreateUtil.asyCreateListData 异步创建数据"))

        return menuList
    }

    override fun navigationTitle(): String = "对象创建工具"


}