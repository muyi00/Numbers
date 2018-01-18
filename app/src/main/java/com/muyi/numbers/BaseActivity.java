package com.muyi.numbers;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    /*****************************************************************************************
     * @时间 2017/5/25 12:05
     * @创建人 YangJing
     * 通用ProgressDialog
     ******************************************************************************************/
    private ProgressDialog pd;

    /**
     * 显示进度条
     *
     * @param msg
     * @param cancelable 是否允许点击Back键取消
     */
    public void showProgress(String msg, boolean cancelable) {
        if (pd == null) {
            pd = new ProgressDialog(this);
            // 设置进度条的形式为圆形转动的进度条
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // 设置是否可以通过点击Back键取消
            pd.setCancelable(cancelable);
            // 设置在点击Dialog外是否取消Dialog进度条
            pd.setCanceledOnTouchOutside(false);
        }
        pd.setMessage(msg);
        pd.show();
    }

    /**
     * 设置显示信息
     *
     * @param msg
     */
    public void setProgressMsg(String msg) {
        if (pd != null) {
            pd.setMessage(msg);
        }
    }

    /**
     * 关闭水平Progress
     */
    public void dismissProgress() {
        if (pd != null) {
            if (!this.isFinishing() && pd.isShowing()) {
                pd.dismiss();
            }
        }
    }


}
