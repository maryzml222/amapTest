package com.example.mylibrary.domain.model.response.passMatchedCar;

import com.google.gson.annotations.SerializedName;

/**
 * Author:  LiXiao
 * Email:   lixiao@kashuo.com
 * Date:    2017/8/16
 * Description:
 */

public class UserInfoEntity {

    @SerializedName("cellphoneNumber")
    public String cellphoneNumber;//用户电话

    @SerializedName("lastName")
    public String lastName;//姓

    @SerializedName("firstName")
    public String firstName;//名

    @SerializedName("sex")
    public int sex;//性别: 0: 未知 1: 男 2: 女

    @SerializedName("companyShortName")
    public String companyShortName;//公司简称

    @SerializedName("companyFullName")
    public String companyFullName;//公司全称

}
