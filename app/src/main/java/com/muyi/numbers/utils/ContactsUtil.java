package com.muyi.numbers.utils;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.muyi.numbers.BaseActivity;
import com.muyi.numbers.entity.ContactsNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/mainfest/article/details/44175051
 * Created by YJ on 2018/1/19.
 */

public class ContactsUtil {

    private BaseActivity baseActivity;

    public ContactsUtil(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    /**
     * 清空通讯录
     */
    public void deleteNumber() {
        baseActivity.showProgress("正在清空通讯录...", false);
        new Thread(new Runnable() {
            public void run() {
                List<ContactsNumber> phoneNumbers = getPhoneNumber(baseActivity);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < phoneNumbers.size(); i++) {
                    delContact(baseActivity, phoneNumbers.get(i).getNumber());
                }
                baseActivity.dismissProgress();
            }
        }).start();

    }


    /**
     * 获取手机全部电话号码
     *
     * @param mContext
     * @return
     */
    private List<ContactsNumber> getPhoneNumber(Context mContext) {
        List<ContactsNumber> numbers = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null); //传入正确的uri
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                ContactsNumber number = new ContactsNumber();
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); //获取联系人number
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;
                }
                number.setNumber(phoneNumber);
                numbers.add(number);
            }
        }
        phoneCursor.close();
        return numbers;
    }


    /**
     * 删除通讯录
     *
     * @param mContext
     * @param name
     */
    private void delContact(Context mContext, final String name) {
        Cursor cursor = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data.RAW_CONTACT_ID},
                ContactsContract.Contacts.DISPLAY_NAME + "=?",
                new String[]{name}, null);
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        if (cursor.moveToFirst()) {
            do {
                long Id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
                ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, Id)).build());
                try {
                    mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    /**
     * 写入通讯录
     *
     * @param numberList
     */
    public void addNumber(final List<ContactsNumber> numberList) {
        baseActivity.showProgress("正在添加号码...", false);
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < numberList.size(); i++) {
                    // 创建一个空的ContentValues
                    ContentValues values = new ContentValues();
                    Uri rawcontacturi = baseActivity.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                    long rawcontactid = ContentUris.parseId(rawcontacturi);
                    values.clear();
                    values.put(ContactsContract.Data.RAW_CONTACT_ID, rawcontactid);
                    values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    // 设置联系人电话号码
                    values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, numberList.get(i).getNumber());
                    // 设置电话类型
                    values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                    // 向联系人电话号码URI添加电话号码
                    baseActivity.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
                }
                baseActivity.dismissProgress();
            }
        }).start();
    }
}

