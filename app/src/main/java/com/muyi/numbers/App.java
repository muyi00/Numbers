package com.muyi.numbers;

import android.app.Application;
import android.content.Context;

import com.muyi.numbers.greendao.DaoMaster;
import com.muyi.numbers.greendao.DaoSession;

/**
 * Created by YJ on 2018/1/18.
 */

public class App extends Application {

    private static DaoSession mDaoSession;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "numbers.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
