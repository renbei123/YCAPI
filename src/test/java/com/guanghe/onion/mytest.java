package com.guanghe.onion;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.junit.Test;


public class mytest {
    String jons1 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXiTZ4Q=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false,\"jump\":null},{\"body\":\"B\",\"correct\":true,\"jump\":null},{\"body\":\"C\",\"correct\":false,\"jump\":null}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";
    String jons2 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXiTZ4Q=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false},{\"body\":\"B\",\"correct\":true},{\"body\":\"C\",\"correct\":false}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";

    String st1 = "{\"username\":\"tom\",\"age\":18,\"address\":[{\"province\":\"上海市\"},{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"}]}";
    String st2 = "{\"age\":null, \"username\":\"tom\",\"address\":[{\"province\":\"上海市\"},{\"disrtict\":\"静安区\"},{\"city\":\"上海市\"}]}";

    @Test
    public void dingdingtest() {

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=9dbe37ad8376ada1b3dc8f73e00cb11962c67ead0c01bc9c7dbc0b653d60d4a9");
        OapiRobotSendRequest request = new OapiRobotSendRequest();


//        request.setMsgtype("text");
//        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
//        text.setContent("测试文本消息\n" +
//                "code:400 ; restime:40ms;\n" +
//                "msg:这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”" +
//                "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”" +
//                "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”" +
//                "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”\n" +
//                " url：http://www.baidu.com");
//
//        request.setText(text);
//        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//        at.setAtMobiles(Arrays.asList("13810264865"));
//        request.setAt(at);

//        request.setMsgtype("link");
//        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
//        link.setMessageUrl("https://www.dingtalk.com/");
////        link.setPicUrl("");
//        link.setTitle(" 时代的火车向前开");
//        link.setText("这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。\n" +
//                "而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林");
//        request.setLink(link);

        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("错误预报");
        markdown.setText("## get https://oapi.dingtalk.com/robot/send?access_token=9dbe37ad8376ada1b3dc8f73e00cb11962c67ead0c01bc9c7dbc0b653d60d4a9\r\n" +
                "---\r\n" +
                "+ **code:400; elapseTime:40ms**\r\n" +
                "+ **response body: {而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么}**\r\n" +
                "+ url \r\n" +
                "+ <a href=\"https://www.jianshu.com/u/1f5ac0cf6a8b\" target=\"_blank\">简书</a>\r\n" +
                "[点此查看详细详细](http://jianshu.com)\r\n");
        request.setMarkdown(markdown);


//        request.setMsgtype("actionCard");
//        OapiRobotSendRequest.Actioncard actioncard=new OapiRobotSendRequest.Actioncard();
//        actioncard.setTitle("接口错误");
//        actioncard.setText("#### error  @13810264865\\n" +
//                "code:400  msg:xxxxlkdjlfkdfj 这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。\\n\" +\n" +
//                "//                \"而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么");
//        actioncard.setSingleTitle("setSingleTitle");
//        actioncard.setSingleURL("http://localhost:8081/index");
//        actioncard.setHideAvatar("0");
//        actioncard.setBtnOrientation("0");
//
        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        System.out.println("response:" + response.getBody().toString());
    }


}
