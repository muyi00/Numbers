package com.muyi.numbers.entity;

import cn.bmob.v3.BmobObject;

/**
 * 号码
 * Created by YJ on 2018/1/18.
 */
public class PhoneNumber extends BmobObject {
    /**
     * 省
     */
    private String province;
    /**
     * 市、区
     */
    private String city;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 区号
     */
    private String areaCode;
    /**
     * 运营商
     */
    private String corp;
    /**
     * 前缀
     */
    private String prefix;
    /**
     * 号段
     */
    private String mobile;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
