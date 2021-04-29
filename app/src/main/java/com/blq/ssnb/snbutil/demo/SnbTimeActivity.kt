package com.blq.ssnb.snbutil.demo

import android.view.View
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbLog.e
import blq.ssnb.snbutil.SnbTimeUtil.String2Date
import blq.ssnb.snbutil.SnbTimeUtil.String2Millisecond
import blq.ssnb.snbutil.SnbTimeUtil.date2String
import blq.ssnb.snbutil.SnbTimeUtil.isLeapYear
import blq.ssnb.snbutil.SnbTimeUtil.milliseconds2String
import blq.ssnb.snbutil.SnbTimeUtil.timeDifference
import blq.ssnb.snbutil.SnbTimeUtil.timeDifferenceFormat
import blq.ssnb.snbutil.SnbToast.showSmart
import blq.ssnb.snbutil.kit.SimpleTimeDifferenceFormat
import blq.ssnb.snbview.listener.OnIntervalClickListener
import java.text.ParseException
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
class SnbTimeActivity : SimpleMenuActivity() {
    override fun navigationTitle(): String {
        return "时间format的工具"
    }

    override fun getMenuBeans(): List<MenuBean> {
        val menuBeans: MutableList<MenuBean> = ArrayList()
        menuBeans.add(MenuBean()
                .setMenuTitle("该工具包含了普通的时间转换方法")
                .setMenuSubTitle("format方法包含了safe后缀方法和普通方法,区别就是safe每次都会new一个SimpleDateFormat，而普通的会从缓存里面拿")
                .setOnClickListener { v: View? -> showToast("使用下面的方法吧") })
        menuBeans.add(MenuBean()
                .setMenuTitle("时间转换1、date 对象转换")
                .setMenuSubTitle("使用方法:SnbTimeUtil.date2String")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showToast(date2String("yyyy-MM-dd HH:mm:ss", Date()))
                    }
                })
        )
        menuBeans.add(MenuBean()
                .setMenuTitle("时间转换2、时间戳转换")
                .setMenuSubTitle("使用方法:SnbTimeUtil.milliseconds2String")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showToast(milliseconds2String("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()))
                    }
                })
        )
        menuBeans.add(MenuBean()
                .setMenuTitle("时间转换3、字符串转换为Data时间")
                .setMenuSubTitle("使用方法:SnbTimeUtil.String2Date")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        try {
                            showToast("date对象:" + String2Date("中国时间 2018-02-12 22:33:42", "中国时间 yyyy-MM-dd HH:mm:ss").toString())
                        } catch (e: ParseException) {
                            e.printStackTrace()
                            showToast("转换出错了")
                        }
                    }
                })
        )
        menuBeans.add(MenuBean()
                .setMenuTitle("时间转换4、字符串转换为时间戳")
                .setMenuSubTitle("使用方法:SnbTimeUtil.String2Date")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        try {
                            showToast("时间戳:" + String2Millisecond("中国时间 2018-02-12 22:33:42", "中国时间 yyyy-MM-dd HH:mm:ss"))
                        } catch (e: ParseException) {
                            e.printStackTrace()
                            showToast("转换出错了")
                        }
                    }
                })
        )
        menuBeans.add(MenuBean()
                .setMenuTitle("判断是否为闰年")
                .setMenuSubTitle("使用方法:SnbTimeUtil.isLeapYear")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        showToast("2017是闰年吗:" + isLeapYear(2017) + ",2020是闰年吗:" + isLeapYear(2020))
                    }
                })
        )
        menuBeans.add(MenuBean()
                .setMenuTitle("计算两个时间的时间差")
                .setMenuSubTitle("使用SnbTimeUtil.timeDifferenceFormat(时间差，)")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        val calendar = Calendar.getInstance()
                        calendar[2019, 0] = 1
                        val diffTime = timeDifference(calendar.time, Date())
                        showToast("当前时间距离2019年1月1日的时间差为:"
                                + diffTime)
                        e("时间:" + milliseconds2String("yyyy-MM-dd,HH:mm:ss", diffTime))
                    }
                }))
        menuBeans.add(MenuBean()
                .setMenuTitle("format 时间差")
                .setMenuSubTitle("使用SnbTimeUtil.timeDifferenceFormat(时间差，format格式)，该功能设计的不太好")
                .setOnClickListener(object : OnIntervalClickListener() {
                    override fun onEffectiveClick(v: View) {
                        val calendar = Calendar.getInstance()
                        calendar[2019, 0] = 1
                        val diffTime = timeDifference(calendar.time, Date())
                        showToast("当前时间距离2019年1月1日的时间差格式化后为:" + timeDifferenceFormat(diffTime, SimpleTimeDifferenceFormat()))
                    }
                }))
        return menuBeans
    }

    private fun showToast(msg: String) {
        showSmart(msg)
    }
}