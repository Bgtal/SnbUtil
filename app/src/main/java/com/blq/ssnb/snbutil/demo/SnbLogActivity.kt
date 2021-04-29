package com.blq.ssnb.snbutil.demo

import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbLog

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/7
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class SnbLogActivity : SimpleMenuActivity() {
    override fun navigationTitle(): String = "SnbLog工具"

    override fun getMenuBeans(): MutableList<MenuBean> {
        val menus = ArrayList<MenuBean>()

        menus.add(MenuBean()
                .setMenuTitle("简单使用")
                .setMenuSubTitle("使用前需要初始化,使用SnbLog.getGlobalBuilder()来进行配置,配置内容包括如下"))

        menus.add(MenuBean().setMenuTitle("1.打就或关闭所有的Log打印")
                .setMenuSubTitle("使用SnbLog.setGlobalLogPrint(boolean),false 将拦截所有的SnbLog的打印方法，" +
                        "true,将开启打印，具体是否打印需要看对应的tagBuilder的配置")
                .setOnClickListener {
                    SnbLog.setGlobalLogPrint(true)
                })
        menus.add(MenuBean()
                .setMenuTitle("2.tagBuilder配置")
                .setMenuSubTitle("tagBuilder配置分为两种，一种是默认的，SnbLog.getGlobalBuilder()，" +
                        "该配置影响的 例如 SnbLog.e() 方法的展示。" +
                        "第二种是自定义配置 使用SnbLog.getBuilder(tagName),该配置将影响使用 类似 SnbLog.sv(tagName,content) 对应tagName的方法"))

        menus.add(MenuBean().setMenuTitle("3.tagBuilder 控制开关")
                .setMenuSubTitle("例如 SnbLog.getGlobalBuilder().isOpen(true)，那么 SnbLog.e() 等方法就会显示log，反之不会显示")
                .setOnClickListener {
                    SnbLog.globalBuilder.isOpen(true);
                })

        menus.add(MenuBean()
                .setMenuTitle("4.tagBuilder 控制打印位置的显示")
                .setMenuSubTitle("SnbLog.getGlobalBuilder().isShowLocation(true) true 将会把打印log的位置提示出来，方便查看代码中log的位置，一般用于相似log太多的定位")
                .setOnClickListener {
                    SnbLog.globalBuilder.isShowLocation(true)
                })

        menus.add(MenuBean()
                .setMenuTitle("5.tagBuilder 是否显示log的范围")
                .setMenuSubTitle("SnbLog.getGlobalBuilder().isShowBorderLine(true) true 将会在log的上下位置用虚线分割，如果log太密集就不太好用了，建议在log少的情况下使用，" +
                        "方便在log中一眼看到log位置")
                .setOnClickListener {
                    SnbLog.globalBuilder.isShowBorderLine(true)
                })
        menus.add(MenuBean()
                .setMenuTitle("6.tagBuilder 设置tag")
                .setMenuSubTitle("SnbLog.getGlobalBuilder().setTag(newTag),可以设置tag")
                .setOnClickListener {
                    SnbLog.globalBuilder.setTag("newTag")
                })

        menus.add(MenuBean().setMenuTitle("6.自定义一个Log 的 tagBuilder")
                .setMenuSubTitle("使用该方法，主要是用来区分不同模块中的log，当要调试某个模块的时候可以将其他的log关掉，方便调试。" +
                        "实际使用中，因为SnbLog.e() 这种方法比较顺手，SnbLog.se() 这种方法因为要传tagName就不太顺手，所以要多用用才行")
                .setOnClickListener {
                    SnbLog.getBuilder("TestBuilder")
                            .isOpen(true)
                            .setTag("我是自定义的tag")
                            .isShowLocation(true)
                })

        menus.add(MenuBean()
                .setMenuTitle("打印默认log构造器的log内容,详情请查看log内容")
                .setOnClickListener {
                    printAll()
                    printTagAll("临时写的tag")
                })

        menus.add(MenuBean().setMenuTitle("打印自定义的一个TagBuilder").setOnClickListener {
            printLogTagAll("TestBuilder")
        })

        return menus;
    }


    /**
     * 打印所有方法
     */
    private fun printAll() {
        SnbLog.v("我是SnbLog.v(String)")
        SnbLog.d("我是SnbLog.d(String)")
        SnbLog.i("我是SnbLog.i(String)")
        SnbLog.w("我是SnbLog.w(String)")
        SnbLog.e("我是SnbLog.e(String)")
        SnbLog.a("我是SnbLog.a(String)")
        SnbLog.error(NullPointerException("我是空报错,调用了我是SnbLog.error(Throwable)"))
    }

    private fun printTagAll(tag: String) {
        SnbLog.v(tag, "我是SnbLog.v(String)")
        SnbLog.d(tag, "我是SnbLog.d(String)")
        SnbLog.i(tag, "我是SnbLog.i(String)")
        SnbLog.w(tag, "我是SnbLog.w(String)")
        SnbLog.e(tag, "我是SnbLog.e(String)")
        SnbLog.a(tag, "我是SnbLog.a(String)")
        SnbLog.error(tag, NullPointerException("我是空报错,调用了我是SnbLog.error(Throwable)"))
    }

    private fun printLogTagAll(tagBuilder: String) {
        SnbLog.sv(tagBuilder, "我是SnbLog.sv(buildTag,String)")
        SnbLog.sd(tagBuilder, "我是SnbLog.sd(buildTag,String)")
        SnbLog.si(tagBuilder, "我是SnbLog.si(buildTag,String)")
        SnbLog.sw(tagBuilder, "我是SnbLog.sw(buildTag,String)")
        SnbLog.se(tagBuilder, "我是SnbLog.se(buildTag,String)")
        SnbLog.sa(tagBuilder, "我是SnbLog.sa(buildTag,String)")
    }

}