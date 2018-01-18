package com.muyi.numbers.utils;

import com.muyi.numbers.BaseActivity;
import com.muyi.numbers.greendao.DaoSession;

/**
 * Created by YJ on 2018/1/18.
 */

public class DistrictUtil {

    private BaseActivity baseActivity;

    public DistrictUtil(BaseActivity baseActivity, DaoSession mDaoSession) {
        this.baseActivity = baseActivity;
        if (districtEmpty(mDaoSession)) {
            leadinginTask();
        }
    }

    /**
     * 判断本地District 表是否为空
     *
     * @param mDaoSession
     * @return
     */
    private boolean districtEmpty(DaoSession mDaoSession) {
        if (mDaoSession.getDistrictDao().count() > 100) {
            return false;
        }
        return true;
    }

    /**
     * 导入任务
     */
    private void leadinginTask() {
        new AsyncTaskUtil(new AsyncTaskUtil.AsyncCallBack() {
            @Override
            public void onPreExecute() {
                baseActivity.setProgressMsg("正在初始化...");
            }

            @Override
            public int doInBackground() throws InterruptedException {

                return 0;
            }

            @Override
            public void onProgressUpdate(int values) {

            }

            @Override
            public void onPostExecute(int values) {
                baseActivity.dismissProgress();
            }
        }).executeTask();
    }


    private void loadData(){
        Util.readAssetsTxt(baseActivity,"District");
    }

}
