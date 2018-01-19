package com.muyi.numbers.utils;

import android.os.AsyncTask;

import com.muyi.numbers.BaseActivity;
import com.muyi.numbers.entity.District;
import com.muyi.numbers.greendao.DaoSession;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJ on 2018/1/18.
 */

public class DistrictUtil {

    private static final String DISTRICT_FILE_NAME = "District.txt";


    private BaseActivity baseActivity;
    private DaoSession mDaoSession;
    private OnLoadOverCallBack callBack;

    public DistrictUtil(BaseActivity baseActivity, DaoSession mDaoSession, OnLoadOverCallBack callBack) {
        this.baseActivity = baseActivity;
        this.mDaoSession = mDaoSession;
        this.callBack = callBack;
        if (districtEmpty()) {
            new DistrictTask().execute();
        } else {
            if (callBack != null) {
                callBack.onLoadOver();
            }
        }
    }

    /**
     * 判断本地District 表是否为空
     *
     * @return
     */
    public boolean districtEmpty() {
        if (mDaoSession.getDistrictDao().count() > 100) {
            return false;
        }
        return true;
    }


    private class DistrictTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            baseActivity.showProgress("正在初始化...", false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<District> districtList = getDistrictList();
            if (districtList.size() > 0) {
                try {
                    mDaoSession.getDistrictDao().deleteAll();
                    mDaoSession.getDistrictDao().insertInTx(districtList);
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            baseActivity.dismissProgress();
            if (callBack != null) {
                callBack.onLoadOver();
            }
        }
    }


    private List<District> getDistrictList() {
        List<District> districts = new ArrayList<>();
        String filePath = FileUtil.getDiskCacheDir(baseActivity) + "/" + DISTRICT_FILE_NAME;
        FileUtil.copyFilesFromAssets(baseActivity, DISTRICT_FILE_NAME, filePath);

        InputStream instream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            instream = new FileInputStream(filePath);
            isr = new InputStreamReader(instream, "UTF-8");
            br = new BufferedReader(isr);
            String line = "";
            String[] arrs = null;
            while ((line = br.readLine()) != null) {
                arrs = line.split(",");
                if (arrs != null && arrs.length == 10) {
                    District d = new District();
                    d.setLevelType(arrs[0]);
                    d.setParentAdminCode(arrs[1]);
                    d.setPinyin(arrs[2]);
                    d.setLng(arrs[3]);
                    d.setZipCode(arrs[4]);
                    d.setAdminCode(arrs[5]);
                    d.setLat(arrs[6]);
                    d.setCityCode(arrs[7]);
                    d.setName(arrs[8]);
                    d.setShortName(arrs[9]);
                    districts.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return districts;
    }


    public interface OnLoadOverCallBack {
        void onLoadOver();
    }
}
