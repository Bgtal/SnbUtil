package com.blq.ssnb.snbutil.demo;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blq.ssnb.snbutil.MApplication;
import com.blq.ssnb.snbutil.R;
import com.blq.ssnb.snbutil.view.ShotCanvasView;

import java.util.ArrayList;
import java.util.List;

import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbutil.SnbScreenUtil;
import blq.ssnb.snbutil.SnbToast;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/11
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbScreenActivity extends SimpleMenuActivity {

    private TextView infoShowView;
    private ImageView screenShotShowView;
    private ShotCanvasView shotRangeView;
    private View showRootView;

    @Override
    protected int contentView() {
        return R.layout.activity_snb_screen;
    }

    @Override
    protected void initView() {
        super.initView();
        infoShowView = findViewById(R.id.tv_info_show);
        screenShotShowView = findViewById(R.id.img_screen_shot_show);
        shotRangeView = findViewById(R.id.scv_shot_range);
        showRootView = findViewById(R.id.ll_show_root_view);

    }

    @Override
    protected String navigationTitle() {
        return "屏幕工具SnbScreenUtil";
    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> menuBeans = new ArrayList<>();

        menuBeans.add(new MenuBean()
                .setMenuTitle("屏幕工具,主要是一些界面")
                .setOnClickListener(v -> {
                    SnbToast.showSmart("点击下面的按钮试一试功能吧");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取App所在屏幕的宽度")
                .setMenuSubTitle("如果有分屏，即分屏状态下app所占的宽度")
                .setOnClickListener(v -> {
                    updateInfo("APP所在屏幕宽度:" + SnbScreenUtil.getAppScreenWidth(getActivity()) + "px");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取屏幕的可用宽度(不包括导航栏)")
                .setMenuSubTitle("屏幕显示的宽度,即使在分屏状态下，宽度依然是屏幕显示宽度")
                .setOnClickListener(v -> {
                    updateInfo("可用屏幕宽度:" + SnbScreenUtil.getAvailableScreenWidth(getApplication()) + "px");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取手机屏幕的真正宽度")
                .setMenuSubTitle("包括底部导航栏的屏幕真正宽度")
                .setOnClickListener(v -> {
                    updateInfo("真正手机屏幕宽度:" + SnbScreenUtil.getFullScreenWidth(MApplication.getContext()) + "px");
                }));


        menuBeans.add(new MenuBean()
                .setMenuTitle("获取App所在屏幕的高度")
                .setMenuSubTitle("如果有分屏,即分屏状态下app所占的高度")
                .setOnClickListener(v -> {
                    updateInfo("APP所在屏幕高度:" + SnbScreenUtil.getAppScreenHeight(getActivity()) + "px");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取屏幕的可用高度(不包括导航栏)")
                .setMenuSubTitle("屏幕的显示高度，即使在分屏状态下，高度依然是屏幕的显示高度")
                .setOnClickListener(v -> {
                    updateInfo("可用屏幕高度:" + SnbScreenUtil.getAvailableScreenHeight(getApplication()) + "px");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取手机屏幕的真正高度")
                .setMenuSubTitle("手机真正的高度，包含底部导航栏")
                .setOnClickListener(v -> {
                    updateInfo("真正手机屏幕高度:" + SnbScreenUtil.getFullScreenHeight(getContext()) + "px");
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获取顶部状态栏的高度")
                .setOnClickListener(v -> {
                    updateInfo("状态栏高度:" + SnbScreenUtil.getStatusHeight(getContext()));
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("获得底部导航栏的高度")
                .setMenuSubTitle("如果像华为这种可以手动缩放底部导航栏的，获取到的数据是不准确的")
                .setOnClickListener(v -> {
                    updateInfo("虚拟导航栏的高度:" + SnbScreenUtil.getNavigationHeight(getContext()));
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("截取当前Item")
                .setMenuSubTitle("以view为对象，截取view布局的对象")
                .setOnClickListener(v -> {
                    updateShotView(SnbScreenUtil.screenShot(v));
                }));
        menuBeans.add(new MenuBean()
                .setMenuTitle("截取当前Activity")
                .setMenuSubTitle("截取当前activity只是能获取当前activity所显示的界面")
                .setOnClickListener(v -> {
                    updateShotView(SnbScreenUtil.screenShot(getActivity()));
                }));


        menuBeans.add(new MenuBean()
                .setMenuTitle("点击自定义截图")
                .setMenuSubTitle("")
                .setOnClickListener(v -> {
                    shotRangeView.openShot();
                }));
        return menuBeans;
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        shotRangeView.setOnActionListener(new ShotCanvasView.OnActionListener() {
            @Override
            public void onActionDown(float sx, float sy) {
                updateInfo("起始位置:(" + sx + "," + sy + ")");
            }

            @Override
            public void onActionMove(float sx, float sy, float ex, float ey) {
                updateInfo("截屏范围:(" + sx + "," + sy + ");当前位置:(" + ex + "," + ey + ")");
            }

            @Override
            public void onActionUp(float sx, float sy, float ex, float ey) {
                screenshot(sx, sy, ex, ey);
            }
        });
    }

    private void screenshot(float sx, float sy, float ex, float ey) {
        if (sx > ex) {
            float tx;
            tx = sx;
            sx = ex;
            ex = tx;
        }
        if (sy > ey) {
            float ty;
            ty = sy;
            sy = ey;
            ey = ty;
        }

        float w = ex - sx;
        float h = ey - sy;

        updateInfo("截屏大小:(" + w + "," + h + ")");
        Bitmap bitmap = SnbScreenUtil.screenShot(showRootView, (int) sx, (int) sy, (int) w, (int) h);
        if (bitmap != null) {
            updateShotView(bitmap);
        } else {
            SnbLog.e("bitmap == null");
            updateInfo("截图至少需要1x1的大小");
        }
    }

    private void updateInfo(String msg) {
        infoShowView.setText(msg);
    }

    private void updateShotView(Bitmap bitmap) {
        screenShotShowView.setImageBitmap(bitmap);
    }
}
