package com.blq.ssnb.snbutil.demo

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbLog.e
import blq.ssnb.snbutil.SnbScreenUtil.getAppScreenHeight
import blq.ssnb.snbutil.SnbScreenUtil.getAppScreenWidth
import blq.ssnb.snbutil.SnbScreenUtil.getAvailableScreenHeight
import blq.ssnb.snbutil.SnbScreenUtil.getAvailableScreenWidth
import blq.ssnb.snbutil.SnbScreenUtil.getFullScreenHeight
import blq.ssnb.snbutil.SnbScreenUtil.getFullScreenWidth
import blq.ssnb.snbutil.SnbScreenUtil.getNavigationHeight
import blq.ssnb.snbutil.SnbScreenUtil.getStatusHeight
import blq.ssnb.snbutil.SnbScreenUtil.screenShot
import blq.ssnb.snbutil.SnbToast.showSmart
import com.blq.ssnb.snbutil.MApplication
import com.blq.ssnb.snbutil.R
import com.blq.ssnb.snbutil.view.ShotCanvasView
import com.blq.ssnb.snbutil.view.ShotCanvasView.OnActionListener
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
class SnbScreenActivity : SimpleMenuActivity() {
    private var infoShowView: TextView? = null
    private var screenShotShowView: ImageView? = null
    private var shotRangeView: ShotCanvasView? = null
    private var showRootView: View? = null
    override fun contentView(): Int {
        return R.layout.activity_snb_screen
    }

    override fun initView() {
        super.initView()
        infoShowView = findViewById(R.id.tv_info_show)
        screenShotShowView = findViewById(R.id.img_screen_shot_show)
        shotRangeView = findViewById(R.id.scv_shot_range)
        showRootView = findViewById(R.id.ll_show_root_view)
    }

    override fun navigationTitle(): String {
        return "屏幕工具SnbScreenUtil"
    }

    override fun getMenuBeans(): List<MenuBean> {
        val menuBeans: MutableList<MenuBean> = ArrayList()
        menuBeans.add(MenuBean()
                .setMenuTitle("屏幕工具,主要是一些界面")
                .setOnClickListener { v: View? -> showSmart("点击下面的按钮试一试功能吧") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取App所在屏幕的宽度")
                .setMenuSubTitle("如果有分屏，即分屏状态下app所占的宽度")
                .setOnClickListener { v: View? -> updateInfo("APP所在屏幕宽度:" + getAppScreenWidth(activity) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取屏幕的可用宽度(不包括导航栏)")
                .setMenuSubTitle("屏幕显示的宽度,即使在分屏状态下，宽度依然是屏幕显示宽度")
                .setOnClickListener { v: View? -> updateInfo("可用屏幕宽度:" + getAvailableScreenWidth(application) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取手机屏幕的真正宽度")
                .setMenuSubTitle("包括底部导航栏的屏幕真正宽度")
                .setOnClickListener { v: View? -> updateInfo("真正手机屏幕宽度:" + getFullScreenWidth(MApplication.getContext()) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取App所在屏幕的高度")
                .setMenuSubTitle("如果有分屏,即分屏状态下app所占的高度")
                .setOnClickListener { v: View? -> updateInfo("APP所在屏幕高度:" + getAppScreenHeight(activity) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取屏幕的可用高度(不包括导航栏)")
                .setMenuSubTitle("屏幕的显示高度，即使在分屏状态下，高度依然是屏幕的显示高度")
                .setOnClickListener { v: View? -> updateInfo("可用屏幕高度:" + getAvailableScreenHeight(application) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取手机屏幕的真正高度")
                .setMenuSubTitle("手机真正的高度，包含底部导航栏")
                .setOnClickListener { v: View? -> updateInfo("真正手机屏幕高度:" + getFullScreenHeight(context) + "px") })
        menuBeans.add(MenuBean()
                .setMenuTitle("获取顶部状态栏的高度")
                .setOnClickListener { v: View? -> updateInfo("状态栏高度:" + getStatusHeight(context)) })
        menuBeans.add(MenuBean()
                .setMenuTitle("获得底部导航栏的高度")
                .setMenuSubTitle("如果像华为这种可以手动缩放底部导航栏的，获取到的数据是不准确的")
                .setOnClickListener { v: View? -> updateInfo("虚拟导航栏的高度:" + getNavigationHeight(context)) })
        menuBeans.add(MenuBean()
                .setMenuTitle("截取当前Item")
                .setMenuSubTitle("以view为对象，截取view布局的对象")
                .setOnClickListener { v: View? -> updateShotView(screenShot(v!!)) })
        menuBeans.add(MenuBean()
                .setMenuTitle("截取当前Activity")
                .setMenuSubTitle("截取当前activity只是能获取当前activity所显示的界面")
                .setOnClickListener { v: View? -> updateShotView(screenShot(activity)) })
        menuBeans.add(MenuBean()
                .setMenuTitle("点击自定义截图")
                .setMenuSubTitle("")
                .setOnClickListener { v: View? -> shotRangeView!!.openShot() })
        return menuBeans
    }

    override fun bindEvent() {
        super.bindEvent()
        shotRangeView!!.setOnActionListener(object : OnActionListener {
            override fun onActionDown(sx: Float, sy: Float) {
                updateInfo("起始位置:($sx,$sy)")
            }

            override fun onActionMove(sx: Float, sy: Float, ex: Float, ey: Float) {
                updateInfo("截屏范围:($sx,$sy);当前位置:($ex,$ey)")
            }

            override fun onActionUp(sx: Float, sy: Float, ex: Float, ey: Float) {
                screenshot(sx, sy, ex, ey)
            }
        })
    }

    private fun screenshot(sx: Float, sy: Float, ex: Float, ey: Float) {
        var sx = sx
        var sy = sy
        var ex = ex
        var ey = ey
        if (sx > ex) {
            val tx: Float
            tx = sx
            sx = ex
            ex = tx
        }
        if (sy > ey) {
            val ty: Float
            ty = sy
            sy = ey
            ey = ty
        }
        val w = ex - sx
        val h = ey - sy
        updateInfo("截屏大小:($w,$h)")
        val bitmap = screenShot(showRootView!!, sx.toInt(), sy.toInt(), w.toInt(), h.toInt())
        if (bitmap != null) {
            updateShotView(bitmap)
        } else {
            e("bitmap == null")
            updateInfo("截图至少需要1x1的大小")
        }
    }

    private fun updateInfo(msg: String) {
        infoShowView!!.text = msg
    }

    private fun updateShotView(bitmap: Bitmap?) {
        screenShotShowView!!.setImageBitmap(bitmap)
    }
}