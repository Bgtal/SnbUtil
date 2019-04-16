package com.blq.ssnb.snbutil;

import com.blq.ssnb.snbutil.demo.SnbScreenActivity;
import com.blq.ssnb.snbutil.demo.SnbTimeActivity;
import com.blq.ssnb.snbutil.demo.SnbToastActivity;

import java.util.ArrayList;
import java.util.List;

import blq.ssnb.baseconfigure.simple.MenuBean;
import blq.ssnb.baseconfigure.simple.SimpleMenuActivity;

public class MainActivity extends SimpleMenuActivity {

    @Override
    protected String navigationTitle() {
        return null;
    }

    @Override
    protected List<MenuBean> getMenuBeans() {
        List<MenuBean> menuBeans = new ArrayList<>();
        menuBeans.add(new MenuBean().setMenuTitle("SnbToast").setActivityClass(SnbToastActivity.class));
        menuBeans.add(new MenuBean().setMenuTitle("SnbTimeUtil").setActivityClass(SnbTimeActivity.class));
        menuBeans.add(new MenuBean().setMenuTitle("SnbScreenUtil").setActivityClass(SnbScreenActivity.class));
        return menuBeans;
    }
}
