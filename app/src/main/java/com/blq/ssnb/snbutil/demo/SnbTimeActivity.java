package com.blq.ssnb.snbutil.demo;

import android.view.View;

import com.blq.snbview.listener.OnIntervalClickListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;
import blq.ssnb.snbutil.SnbLog;
import blq.ssnb.snbutil.SnbTimeUtil;
import blq.ssnb.snbutil.SnbToast;
import blq.ssnb.snbutil.kit.SimpleTimeDifferenceFormat;

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
public class SnbTimeActivity extends SimpleMenuActivity {
    @Override
    protected String navigationTitle() {
        return "时间format的工具";
    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> menuBeans = new ArrayList<>();
        menuBeans.add(new MenuBean()
                .setMenuTitle("该工具包含了普通的时间转换方法")
                .setMenuSubTitle("format方法包含了safe后缀方法和普通方法,区别就是safe每次都会new一个SimpleDateFormat，而普通的会从缓存里面拿")
                .setOnClickListener(v -> showToast("使用下面的方法吧")));
        menuBeans.add(new MenuBean()
                .setMenuTitle("时间转换1、date 对象转换")
                .setMenuSubTitle("使用方法:SnbTimeUtil.date2String")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        showToast(SnbTimeUtil.date2String("yyyy-MM-dd HH:mm:ss", new Date()));
                    }
                })
        );
        menuBeans.add(new MenuBean()
                .setMenuTitle("时间转换2、时间戳转换")
                .setMenuSubTitle("使用方法:SnbTimeUtil.milliseconds2String")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        showToast(SnbTimeUtil.milliseconds2String("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));
                    }
                })
        );

        menuBeans.add(new MenuBean()
                .setMenuTitle("时间转换3、字符串转换为Data时间")
                .setMenuSubTitle("使用方法:SnbTimeUtil.String2Date")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        try {
                            showToast("date对象:" + SnbTimeUtil.String2Date("中国时间 2018-02-12 22:33:42", "中国时间 yyyy-MM-dd HH:mm:ss").toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            showToast("转换出错了");
                        }
                    }
                })
        );

        menuBeans.add(new MenuBean()
                .setMenuTitle("时间转换4、字符串转换为时间戳")
                .setMenuSubTitle("使用方法:SnbTimeUtil.String2Date")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        try {
                            showToast("时间戳:" + SnbTimeUtil.String2Millisecond("中国时间 2018-02-12 22:33:42", "中国时间 yyyy-MM-dd HH:mm:ss"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            showToast("转换出错了");
                        }
                    }
                })
        );
        menuBeans.add(new MenuBean()
                .setMenuTitle("判断是否为闰年")
                .setMenuSubTitle("使用方法:SnbTimeUtil.isLeapYear")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        showToast("2017是闰年吗:" + SnbTimeUtil.isLeapYear(2017) + ",2020是闰年吗:" + SnbTimeUtil.isLeapYear(2017));
                    }
                })

        );

        menuBeans.add(new MenuBean()
                .setMenuTitle("计算两个时间的时间差")
                .setMenuSubTitle("使用SnbTimeUtil.timeDifferenceFormat(时间差，)")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(2019, 0, 1);
                        long diffTime = SnbTimeUtil.timeDifference(calendar.getTime(), new Date());
                        showToast("当前时间距离2019年1月1日的时间差为:"
                                + diffTime);
                        SnbLog.e("时间:"+SnbTimeUtil.milliseconds2String("yyyy-MM-dd,HH:mm:ss",diffTime));
                    }
                }));
        menuBeans.add(new MenuBean()
                .setMenuTitle("format 时间差")
                .setMenuSubTitle("使用SnbTimeUtil.timeDifferenceFormat(时间差，format格式)，该功能设计的不太好")
                .setOnClickListener(new OnIntervalClickListener() {
                    @Override
                    public void onEffectiveClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(2019, 0, 1);
                        long diffTime = SnbTimeUtil.timeDifference(calendar.getTime(),new Date());
                        showToast("当前时间距离2019年1月1日的时间差格式化后为:" + SnbTimeUtil.timeDifferenceFormat(diffTime, new SimpleTimeDifferenceFormat()));
                    }
                }));
        return menuBeans;
    }

    private void showToast(String msg) {
        SnbToast.showSmart(msg);
    }
}
