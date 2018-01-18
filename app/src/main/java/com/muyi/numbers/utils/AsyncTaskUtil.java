package com.muyi.numbers.utils;

import android.os.AsyncTask;

/**
 * Created by YJ on 2018/1/18.
 */

public class AsyncTaskUtil extends AsyncTask<String, Integer, Integer> {

    private AsyncCallBack callBack;


    public AsyncTaskUtil(AsyncCallBack callBack) {
        super();
        callBack = callBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callBack != null) {
            callBack.onPreExecute();
        }
    }


    @Override
    protected Integer doInBackground(String... params) {
        if (callBack != null) {
            int tt = 0;
            try {
                tt = callBack.doInBackground();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tt;
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (callBack != null) {
            callBack.onProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Integer values) {
        super.onPostExecute(values);
        if (callBack != null) {
            callBack.onPostExecute(values);
        }
    }


    public AsyncTaskUtil executeTask() {
        this.execute();
        return this;
    }

    public interface AsyncCallBack {
        void onPreExecute();

        int doInBackground() throws InterruptedException;

        void onProgressUpdate(int values);

        void onPostExecute(int values);
    }
}
