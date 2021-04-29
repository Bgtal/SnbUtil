package com.blq.ssnb.snbutil.demo

import android.view.View
import android.widget.EditText
import android.widget.TextView
import blq.ssnb.baseconfigure.simple.MenuBean
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity
import blq.ssnb.snbutil.SnbPreferences
import blq.ssnb.snbutil.SnbToast.showSmart
import com.blq.ssnb.snbutil.R
import java.util.*

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/19
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
</pre> *
 */
class SnbPreferencesActivity : SimpleMenuActivity() {
    private lateinit var mPreferences: SnbPreferences
    private lateinit var saveKeyEdit: EditText
    private lateinit var saveValueEdit: EditText
    private lateinit var readKeyEdit: EditText
    private lateinit var readValueView: TextView
    private lateinit var saveContentView: TextView
    override fun contentView(): Int {
        return R.layout.activity_snb_preferences
    }

    override fun initView() {
        super.initView()
        saveKeyEdit = findViewById(R.id.edit_pre_save_key)
        saveValueEdit = findViewById(R.id.edit_pre_save_value)
        readKeyEdit = findViewById(R.id.edit_pre_read_key)
        readValueView = findViewById(R.id.tv_pre_read_value)
        saveContentView = findViewById(R.id.tv_save_content)
    }

    override fun navigationTitle(): String {
        return "SnbPreferences"
    }

    override fun initData() {
        super.initData()
        mPreferences = SnbPreferences(context, "PlistName")
    }

    override fun getMenuBeans(): List<MenuBean> {
        val beans: MutableList<MenuBean> = ArrayList()
        beans.add(MenuBean()
                .setMenuTitle("保存数据")
                .setMenuSubTitle("使用mPreferences.save(key,value) 方法保存要存入的值")
                .setOnClickListener { _: View? ->
                    val key = saveKeyEdit.text.toString()
                    if (key.trim { it <= ' ' }.isEmpty()) {
                        showSmart(msg = "key不能为空")
                    }else{
                        val value = saveValueEdit.text.toString()
                        mPreferences.save(key, value)
                        updateContentView()
                    }
                })
        beans.add(MenuBean()
                .setMenuTitle("读取数据")
                .setMenuSubTitle("读取数据需要使用对应的read方法，例如读取String使用mPreferences.readString(key)")
                .setOnClickListener { _: View? ->
                    val key = readKeyEdit.text.toString()
                    if (key.trim { it <= ' ' }.isEmpty()) {
                        showSmart(msg = "key不能为空")
                    }else{
                        try {
                            var value = mPreferences.readString(key)
                            if (value == "") {
                                value = "value 不存在"
                            }
                            readValueView.text = value
                        } catch (e: Exception) {
                            showSmart(msg = "Demo 只写了读取String类型的Value方法,读取其他类型请使用相应的read方法")
                        }
                    }
                }
        )
        beans.add(MenuBean()
                .setMenuTitle("移除key")
                .setOnClickListener { v: View? ->
                    val key = readKeyEdit.text.toString()
                    if (key.trim { it <= ' ' }.isEmpty()) {
                        showSmart(msg = "key不能为空")
                    }else{
                        mPreferences.remove(key)
                        var value = mPreferences.readString(key)
                        if (value == "") {
                            value = "value 不存在"
                        }
                        readValueView.text = value
                        updateContentView()
                    }
                })
        beans.add(MenuBean().setMenuTitle("清理内容")
                .setOnClickListener { _: View? ->
                    mPreferences.clear()
                    updateContentView()
                    showSmart(msg = "清理完成")
                }
        )
        beans.add(MenuBean()
                .setMenuTitle("添加所有类型")
                .setOnClickListener { _: View? ->
                    mPreferences.save("tkey_1", 10001)
                    mPreferences.save("tkey_2", true)
                    mPreferences.save("tkey_3", 12.4f)
                    mPreferences.save("tkey_4", 3555533343342342323L)
                    mPreferences.save("tkey_5", "我是String")
                    val set: MutableSet<String?> = HashSet()
                    for (i in 0..4) {
                        set.add("set-$i")
                    }
                    mPreferences.save("tkey_6", set)
                    showSmart(msg = "添加所有类型的Value")
                    updateContentView()
                })
        return beans
    }

    private fun updateContentView() {
        val sn = """存储xml文件:${mPreferences.fileName} 
            |内容:${mPreferences.readAll()}""".trimMargin()
        saveContentView.text = sn
    }
}