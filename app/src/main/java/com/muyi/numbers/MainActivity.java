package com.muyi.numbers;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTopBarColor(R.color.colorPrimary);
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarTb);
        Bmob.initialize(this, "01d88fc32ae11655cd0f1df65ce46fde");

//        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//        bmobQuery.getObject("6b6c11c537", new >QueryListener<Person>() {
//            @Override
//            public void done(Person object,BmobException e) {
//                if(e==null){
//                    toast("查询成功");
//                }else{
//                    toast("查询失败：" + e.getMessage());
//                }
//            }
//        });
    }
}
