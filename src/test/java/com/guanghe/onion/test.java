package com.guanghe.onion;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;

import java.util.Arrays;

public class test {
//    myobject o1=new myobject();
//    o1.
//    JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3)))

    public static void main(String[] arg) {
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName1.setName("明细-单行输入框示例");
        ItemName1.setValue("明细-单行输入框value");
        // 明细-多行输入框
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName2.setName("明细-多行输入框示例");
        ItemName2.setValue("明细-多行输入框value");
        // 明细-照片
        OapiProcessinstanceCreateRequest.FormComponentValueVo ItemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        ItemName3.setName("明细-图片示例");
        ItemName3.setValue("[\"http://xxxxx\"]");

        // 明细
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("明细示例");
        vo4.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(ItemName1, ItemName2, ItemName3))));


    }


}


