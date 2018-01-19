package com.muyi.numbers.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import cn.bmob.v3.BmobObject;

/**
 * 地区
 * Created by YJ on 2018/1/18.
 */
@Entity
public class District extends BmobObject {

    @Id(autoincrement = true)
    private Long id;
    /**
     * id
     */
    private String adminCode;
    /**
     * 所属父级id
     */
    private String parentAdminCode;
    /**
     * 级别类型
     */
    private String levelType;
    /**
     * 名称
     */
    private String name;
    /**
     * 短名称
     */
    private String shortName;
    /**
     * 拼音
     */
    private String pinyin;
    /**
     * 区号
     */
    private String cityCode;
    /**
     * 邮政编码
     */
    private String zipCode;
    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    @Generated(hash = 1405435303)
    public District(Long id, String adminCode, String parentAdminCode,
            String levelType, String name, String shortName, String pinyin,
            String cityCode, String zipCode, String lng, String lat) {
        this.id = id;
        this.adminCode = adminCode;
        this.parentAdminCode = parentAdminCode;
        this.levelType = levelType;
        this.name = name;
        this.shortName = shortName;
        this.pinyin = pinyin;
        this.cityCode = cityCode;
        this.zipCode = zipCode;
        this.lng = lng;
        this.lat = lat;
    }

    @Generated(hash = 1876777828)
    public District() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminCode() {
        return this.adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }

    public String getParentAdminCode() {
        return this.parentAdminCode;
    }

    public void setParentAdminCode(String parentAdminCode) {
        this.parentAdminCode = parentAdminCode;
    }

    public String getLevelType() {
        return this.levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }


}
