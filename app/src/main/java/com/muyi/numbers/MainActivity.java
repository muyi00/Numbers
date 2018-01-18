package com.muyi.numbers;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.muyi.numbers.entity.District;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTopBarColor(R.color.colorPrimary);
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarTb);
        Bmob.initialize(this, "01d88fc32ae11655cd0f1df65ce46fde");

        BmobQuery<District> query = new BmobQuery<District>();
        query.count(District.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if (e == null) {
                    showToast("count对象个数为：" + count);
                } else {
                    showToast("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
