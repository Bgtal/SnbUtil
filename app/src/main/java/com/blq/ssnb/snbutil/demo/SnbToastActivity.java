package com.blq.ssnb.snbutil.demo;

import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;
import blq.ssnb.snbutil.SToast;
import blq.ssnb.snbutil.SnbToast;
import blq.ssnb.snbview.listener.OnIntervalClickListener;

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
public class SnbToastActivity extends SimpleMenuActivity {

    @Override
    protected String navigationTitle() {
        return "ToastUtil 演示";
    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> menuBeans = new ArrayList<>();
        menuBeans.add(new MenuBean()
                .setMenuTitle("一、使用全局toast需要初始化")
                .setMenuSubTitle("建议在application 中调用SnbToast.init(Context)方法，点击初始化")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        SnbToast.init(getApplicationContext());
                        SnbToast.showSmart("初始化成功");
                    }
                }));
        menuBeans.add(new MenuBean()
                .setMenuTitle("长显示")
                .setMenuSubTitle("使用方法 SnbToast.showLong();")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        SnbToast.showLong("我是一个长显示");
                    }
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("短显示")
                .setMenuSubTitle("使用方法 SnbToast.showShort();")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        SnbToast.showShort("我是一个短显示");
                    }
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("自适应显示时间")
                .setMenuSubTitle("使用方法 SnbToast.showSmart(); 根据内容长度自动调用长显示或短显示")
                .setOnClickListener(new OnIntervalClickListener() {
                    private boolean isShowLong = false;

                    @Override
                    public void onEffectiveClick(View v) {
                        if (isShowLong) {
                            SnbToast.showSmart("长度大于20个长度的时候,会调用toast的Toast.LENGTH_LONG属性");
                        } else {
                            SnbToast.showSmart("长度短显示时间短");
                        }
                        isShowLong = !isShowLong;
                    }
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("二、使用当前Context调用Toast")
                .setMenuSubTitle("使用方式也是Long,Short,smart三种，不同点是方法都增加了Context参数")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        SnbToast.showSmart(getContext(), "使用Context调用的Toast不会被后面调用的Toast覆盖掉");
                        SnbToast.showSmart("我是紧跟着的第一个全局Toast");
                        SnbToast.showSmart("我是紧跟着的第二个全局Toast");
                    }
                }));

        menuBeans.add(new MenuBean()
                .setMenuTitle("两种方式都可以在子线程里面直接调用")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        new Thread(() -> SnbToast.showSmart("我是子线程方法:" + Thread.currentThread().toString())).start();
                    }
                }));

        menuBeans.add(new MenuBean().setMenuTitle("newToast").setOnClickListener(new OnIntervalClickListener() {
            @Override
            public void onEffectiveClick(View v) {
                SToast.makeText(getContext(),"aaaaa", Toast.LENGTH_LONG).show();
            }
        }));
        return menuBeans;
    }
}
