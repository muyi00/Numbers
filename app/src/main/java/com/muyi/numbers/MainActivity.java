package com.muyi.numbers;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.muyi.numbers.adapter.CommonAdapter;
import com.muyi.numbers.adapter.ViewHolder;
import com.muyi.numbers.entity.ContactsNumber;
import com.muyi.numbers.entity.District;
import com.muyi.numbers.entity.PhoneNumber;
import com.muyi.numbers.greendao.DistrictDao;
import com.muyi.numbers.utils.ContactsUtil;
import com.muyi.numbers.utils.DistrictUtil;
import com.muyi.numbers.utils.Util;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Spinner mProvinceSp;
    private Spinner mCitySp;
    private List<District> provinceList = new ArrayList<>();
    private List<District> cityList = new ArrayList<>();
    private CommonAdapter<District> provinceAdapter;
    private CommonAdapter<District> cityAdapter;
    private District cityDistrict;
    private Spinner mOperatorSp;
    private Spinner mCountSp;
    /**
     * 清除通讯录
     */
    private Button mClearContactsBtn;
    /**
     * 生成号码
     */
    private Button mCreateNumberBtn;
    /**
     * 写入通讯录
     */
    private Button mWriteNumberBtn;
    private ListView mNumbersLv;
    private ContactsUtil contactsUtil;
    private List<ContactsNumber> contactsNumberList = new ArrayList<>();
    private CommonAdapter<ContactsNumber> contactsNumberdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "01d88fc32ae11655cd0f1df65ce46fde");
        setContentView(R.layout.activity_main);
        initView();
        setTopBarColor(R.color.colorPrimary);
        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarTb);
        contactsUtil = new ContactsUtil(this);
        mProvinceSp = (Spinner) findViewById(R.id.province_sp);
        provinceAdapter = new CommonAdapter<District>(mContext, provinceList, android.R.layout.simple_spinner_item) {
            @Override
            public void convert(ViewHolder helper, District item) {
                helper.setText(android.R.id.text1, item.getName());
            }
        };
        mProvinceSp.setAdapter(provinceAdapter);
        mProvinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityDistrict = null;
                District provinceDistrict = provinceList.get(i);
                cityList.clear();
                QueryBuilder<District> queryBuilder = App.getDaoSession().getDistrictDao().queryBuilder();
                queryBuilder.where(DistrictDao.Properties.ParentAdminCode.eq(provinceDistrict.getAdminCode()));
                queryBuilder.where(DistrictDao.Properties.LevelType.eq(2));
                cityList.addAll(queryBuilder.build().list());
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mCitySp = (Spinner) findViewById(R.id.city_sp);
        cityAdapter = new CommonAdapter<District>(mContext, cityList, android.R.layout.simple_spinner_item) {
            @Override
            public void convert(ViewHolder helper, District item) {
                helper.setText(android.R.id.text1, item.getName());
            }
        };
        mCitySp.setAdapter(cityAdapter);
        mCitySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityDistrict = cityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        new DistrictUtil(this, App.getDaoSession(), new DistrictUtil.OnLoadOverCallBack() {
            @Override
            public void onLoadOver() {
                provinceList.clear();
                QueryBuilder<District> queryBuilder = App.getDaoSession().getDistrictDao().queryBuilder();
                queryBuilder.where(DistrictDao.Properties.ParentAdminCode.eq(100000));
                queryBuilder.where(DistrictDao.Properties.LevelType.eq(1));
                provinceList.addAll(queryBuilder.build().list());
                provinceAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        mOperatorSp = (Spinner) findViewById(R.id.operator_sp);
        mCountSp = (Spinner) findViewById(R.id.count_sp);
        mClearContactsBtn = (Button) findViewById(R.id.clear_contacts_btn);
        mClearContactsBtn.setOnClickListener(this);
        mCreateNumberBtn = (Button) findViewById(R.id.create_number_btn);
        mCreateNumberBtn.setOnClickListener(this);
        mWriteNumberBtn = (Button) findViewById(R.id.write_number_btn);
        mWriteNumberBtn.setOnClickListener(this);
        mNumbersLv = (ListView) findViewById(R.id.numbers_lv);
        contactsNumberdapter = new CommonAdapter<ContactsNumber>(mContext, contactsNumberList, R.layout.contacts_number_layout) {
            @Override
            public void convert(ViewHolder helper, ContactsNumber item) {
                helper.setText(R.id.number_tv, item.getNumber());
            }
        };
        mNumbersLv.setAdapter(contactsNumberdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_contacts_btn:
                contactsUtil.deleteNumber();
                break;
            case R.id.create_number_btn:
                createNumber();
                break;
            case R.id.write_number_btn:
                if (contactsNumberList.size() <= 0) {
                    showToast("没有可写入的号码");
                    return;
                }
                contactsUtil.addNumber(contactsNumberList);
                break;
        }
    }

    private void createNumber() {
        if (cityDistrict == null) {
            showToast("请选择有限行政地区");
            return;
        }
        showProgress("正在创建电话号码...", false);
        int count = 50;
        try {
            count = Integer.parseInt(mCountSp.getSelectedItem().toString());
        } catch (Exception e) {

        }
        //平均每个号码段生成的数量
        final int average = count / 5;

        String corpName = mOperatorSp.getSelectedItem().toString();
        String corp;
        if ("中国移动".equals(corpName)) {
            corp = "10086";
        } else if ("中国联通".equals(corpName)) {
            corp = "10010";
        } else {
            corp = "10000";
        }

        BmobQuery<PhoneNumber> query = new BmobQuery<PhoneNumber>();
        query.addWhereEqualTo("areaCode", cityDistrict.getCityCode());
        query.addWhereEqualTo("corp", corp);
        query.setLimit(500);
        query.findObjects(new FindListener<PhoneNumber>() {
            @Override
            public void done(List<PhoneNumber> list, BmobException e) {
                if (e != null) {
                    dismissProgress();
                    showToast("失败：" + e.getMessage() + "," + e.getErrorCode());
                }

                //从号段中随机取出5个
                List<Integer> indexList = Util.getRandomNumber(0, list.size(), 5);
                contactsNumberList.clear();
                //获取号段集合
                for (int i = 0; i < indexList.size(); i++) {
                    //前七位
                    String str7 = list.get(indexList.get(i)).getMobile();
                    //后四位
                    List<Integer> suffixList = Util.getRandomNumber(1000, 9999, average);
                    for (int j = 0; j < suffixList.size(); j++) {
                        contactsNumberList.add(new ContactsNumber(str7 + suffixList.get(j)));
                    }
                }
                contactsNumberdapter.notifyDataSetChanged();
                dismissProgress();
            }
        });

    }

}
