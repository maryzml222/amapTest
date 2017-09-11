package com.example.mylibrary.domain.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * autour: Jack.Lee | Li.Xiao
 * email: jxiaolee@aliyun.com
 * date: 2017/8/03 23:50
 * version: v1.0.0
 * description: 订单支付功能
 */

public class PassPayRequest {
    //Todo...需要更新请求的参数信息

    @SerializedName("amount")
    public int amount;//支付金额，单位：分 ,

    @SerializedName("body")
    public String body;//商品的描述信息，该参数最长为 128 个 Unicode 字符，yeepay_wap 对于该参数长度限制为 100 个 Unicode 字符 ,

    @SerializedName("cellphoneNumber")
    public int cellphoneNumber;//用户手机号 ,

    @SerializedName("channel")
    public int channel;//支付使用的第三方支付渠道, 取值参考：https://www.pingxx.com/api?language=Java#支付渠道属性值 ,

    @SerializedName("clientIP")
    public int clientIP;// 发起支付请求客户端的 IPv4 地址，如: 127.0.0.1 ,

    @SerializedName("subject")
    public int subject;// 商品的标题，该参数最长为 32 个 Unicode 字符，银联全渠道（ upacp / upacp_wap ）限制在 32 个字节

}
