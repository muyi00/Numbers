package com.muyi.numbers;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

/**
 * Created by YJ on 2017/12/29.
 */

public class BaseActivity extends AppCompatActivity {



    /**
     * 设置TopBar透明度
     */
    public void setTopBarTranslucent() {
        //设置状态栏半透明
        // StatusBarUtil.setTranslucent(BaseActivity.this, 112);// statusBarAlpha 该值需要在 0 ~ 255 之间
        //设置状态栏全透明
        StatusBarUtil.setTransparent(BaseActivity.this);
    }

    /**
     * 设置TopBar颜色
     */
    public void setTopBarColor(int id) {
        //设置状态栏颜色
        StatusBarUtil.setColorNoTranslucent(BaseActivity.this, ContextCompat.getColor(this, id));
    }



}
