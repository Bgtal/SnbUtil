package com.blq.ssnb.snbutil.demo;

import android.widget.EditText;
import android.widget.TextView;

import com.blq.ssnb.snbutil.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;
import blq.ssnb.snbutil.SnbPreferences;
import blq.ssnb.snbutil.SnbToast;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/19
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbPreferencesActivity extends SimpleMenuActivity {

    private SnbPreferences mPreferences;

    private EditText saveKeyEdit;
    private EditText saveValueEdit;
    private EditText readKeyEdit;
    private TextView readValueView;
    private TextView saveContentView;

    @Override
    protected int contentView() {
        return R.layout.activity_snb_preferences;
    }

    @Override
    protected void initView() {
        super.initView();
        saveKeyEdit = findViewById(R.id.edit_pre_save_key);
        saveValueEdit = findViewById(R.id.edit_pre_save_value);
        readKeyEdit = findViewById(R.id.edit_pre_read_key);
        readValueView = findViewById(R.id.tv_pre_read_value);
        saveContentView = findViewById(R.id.tv_save_content);

    }

    @Override
    protected String navigationTitle() {
        return "SnbPreferences";
    }

    @Override
    protected void initData() {
        super.initData();
        mPreferences = new SnbPreferences(getContext(), "PlistName");

    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> beans = new ArrayList<>();

        beans.add(new MenuBean()
                .setMenuTitle("保存数据")
                .setMenuSubTitle("使用mPreferences.save(key,value) 方法保存要存入的值")
                .setOnClickListener(v -> {
                    String key = saveKeyEdit.getText().toString();
                    if (key.trim().length() == 0) {
                        SnbToast.showSmart("key不能为空");
                        return;
                    }
                    String value = saveValueEdit.getText().toString();
                    mPreferences.save(key, value);
                    updateContentView();
                }));

        beans.add(new MenuBean()
                .setMenuTitle("读取数据")
                .setMenuSubTitle("读取数据需要使用对应的read方法，例如读取String使用mPreferences.readString(key)")
                .setOnClickListener(v -> {
                    String key = readKeyEdit.getText().toString();
                    if (key.trim().length() == 0) {
                        SnbToast.showSmart("key不能为空");
                        return;
                    }
                    try {
                        String value = mPreferences.readString(key);
                        if (value.equals("")) {
                            value = "value 不存在";
                        }
                        readValueView.setText(value);
                    } catch (Exception e) {
                        SnbToast.showSmart("Demo 只写了读取String类型的Value方法,读取其他类型请使用相应的read方法");
                    }
                })
        );

        beans.add(new MenuBean()
                .setMenuTitle("移除key")
                .setOnClickListener(v -> {
                    String key = readKeyEdit.getText().toString();
                    if (key.trim().length() == 0) {
                        SnbToast.showSmart("key不能为空");
                        return;
                    }
                    mPreferences.remove(key);
                    String value = mPreferences.readString(key);
                    if (value.equals("")) {
                        value = "value 不存在";
                    }
                    readValueView.setText(value);
                    updateContentView();
                }));


        beans.add(new MenuBean().setMenuTitle("清理内容")
                .setOnClickListener(v -> {
                    mPreferences.clear();
                    updateContentView();
                    SnbToast.showSmart("清理完成");
                })
        );

        beans.add(new MenuBean()
                .setMenuTitle("添加所有类型")
                .setOnClickListener(v -> {
                    mPreferences.save("tkey_1", 10001);
                    mPreferences.save("tkey_2", true);
                    mPreferences.save("tkey_3", 12.4f);
                    mPreferences.save("tkey_4", 3555533343342342323L);
                    mPreferences.save("tkey_5", "我是String");
                    Set<String> set = new HashSet<>();
                    for (int i = 0; i < 5; i++) {
                        set.add("set-" + i);
                    }
                    mPreferences.save("tkey_6", set);
                    SnbToast.showSmart("添加所有类型的Value");
                    updateContentView();

                }));

        return beans;
    }

    private void updateContentView() {
        saveContentView.setText("存储xml文件:" + mPreferences.getFileName() + "\n内容:" + mPreferences.readAll().toString());
    }
}
