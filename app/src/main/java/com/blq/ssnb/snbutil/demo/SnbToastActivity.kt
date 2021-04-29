package com.blq.ssnb.snbutil.demo

import android.view.View
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbToast.init
import blq.ssnb.snbutil.SnbToast.showLong
import blq.ssnb.snbutil.SnbToast.showShort
import blq.ssnb.snbutil.SnbToast.showSmart
import blq.ssnb.snbview.listener.OnIntervalClickListener
import java.util.*

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
class SnbToastActivity : SimpleMenuActivity() {
    override fun navigationTitle(): String {
        return "ToastUtil 演示"
    }

    override fun getMenuBeans(): List<MenuBean> {
        val menuBeans: MutableList<MenuBean> = ArrayList()
        menuBeans.add(MenuBean()
                .setMenuTitle("一、使用全局toast需要初始化")
                .setMenuSubTitle("建议在application 中调用SnbToast.init(Context)方法，点击初始化")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        init(applicationContext)
                        showSmart(msg = "初始化成功")
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("长显示")
                .setMenuSubTitle("使用方法 SnbToast.showLong();")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showLong(msg = "我是一个长显示")
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("短显示")
                .setMenuSubTitle("使用方法 SnbToast.showShort();")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showShort(msg = "我是一个短显示")
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("自适应显示时间")
                .setMenuSubTitle("使用方法 SnbToast.showSmart(); 根据内容长度自动调用长显示或短显示")
                .setOnClickListener(object : OnIntervalClickListener() {
                    private var isShowLong = false
                    override fun onEffectiveClick(v: View) {
                        if (isShowLong) {
                            showSmart(msg = "长度大于20个长度的时候,会调用toast的Toast.LENGTH_LONG属性")
                        } else {
                            showSmart(msg = "长度短显示时间短")
                        }
                        isShowLong = !isShowLong
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("二、使用当前Context调用Toast")
                .setMenuSubTitle("使用方式也是Long,Short,smart三种，不同点是方法都增加了Context参数")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showSmart(context, "使用Context调用的Toast不会被后面调用的Toast覆盖掉")
                        showSmart(msg = "我是紧跟着的第一个全局Toast")
                        showSmart(msg = "我是紧跟着的第二个全局Toast")
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("两种方式都可以在子线程里面直接调用")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        Thread { showSmart(msg = "我是子线程方法:" + Thread.currentThread().toString()) }.start()
                    }
                }))
        return menuBeans
    }
}