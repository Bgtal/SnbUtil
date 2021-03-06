package com.blq.ssnb.snbutil

import java.util.ArrayList

import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import com.blq.ssnb.snbutil.demo.*

class MainActivity : SimpleMenuActivity() {

    override fun navigationTitle(): String {
        return "工具类demo演示"
    }

    override fun getMenuBeans(): List<MenuBean> {
        val menuBeans = ArrayList<MenuBean>()
        menuBeans.add(MenuBean().setMenuTitle("SnbToast").setActivityClass(SnbToastActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbTimeUtil").setActivityClass(SnbTimeActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbScreenUtil").setActivityClass(SnbScreenActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbPreferences").setActivityClass(SnbPreferencesActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbLog").setActivityClass(SnbLogActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("FILE").setActivityClass(SnbFileActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbDisplay").setActivityClass(SnbDisplayActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbDateCreate").setActivityClass(SnbDateCreateActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbCountDownTimer").setActivityClass(SnbCountDownTimerActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbRippleCreateFactory").setActivityClass(SnbRippleActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbColorAndDrawableSelectorActivity").setActivityClass(SnbColorAndDrawableSelectorActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbBluetoothManagerActivity").setActivityClass(SnbBluetoothManagerActivity::class.java))
        menuBeans.add(MenuBean().setMenuTitle("SnbUtilActivity").setActivityClass(SnbUtilActivity::class.java))

        return menuBeans
    }
}
