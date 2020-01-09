package com.guanghe.onion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.base.PageData;
import com.guanghe.onion.tools.fastJsonDiff;
import org.junit.Test;

import java.util.List;

public class jsoncompare {

    String jons1 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXi=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false,\"jump\":null},{\"body\":\"B\",\"correct\":true,\"jump\":null},{\"body\":\"C\",\"correct\":false,\"jump\":null}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";
    String jons2 = "{\"_id\":null,\"id\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"name\":\"不等式与不等式的解集重难点片段\",\"thumbnail\":null,\"description\":\"在数轴上表示不等式的解集，是向左还是向右？空心还是实心？想做对比还要画好几条数轴。这个视频直观讲解如何用数轴表示不等式的解集。\",\"order\":null,\"type\":\"clip\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4?e=1557311788&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:oISOF8wVXI32MoA4xHexBXi=\",\"interactions\":[{\"id\":\"3d9956b6-1d01-11e8-803f-076f570ba47c\",\"videoId\":\"3d988ac4-1d01-11e8-803f-47c2df71585f\",\"time\":23,\"jump\":0,\"choices\":[{\"body\":\"A\",\"correct\":false},{\"body\":\"B\",\"correct\":true},{\"body\":\"C\",\"correct\":false}]}],\"clips\":[],\"difficulty\":1,\"addresses\":[{\"id\":\"3d99f29c-1d01-11e8-b83a-5b305127e191\",\"url\":\"https://hls.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"high\"},{\"id\":\"3d99fb7a-1d01-11e8-b83a-b34b3b721b26\",\"url\":\"https://hls.media.yangcong345.com/pcL/pcL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d99fc56-1d01-11e8-b83a-0f72378e4408\",\"url\":\"https://hls.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"pc\",\"format\":\"hls\",\"clarity\":\"middle\"},{\"id\":\"3d99fd14-1d01-11e8-b83a-73781cdaae83\",\"url\":\"http://private.media.yangcong345.com/pcM/pcM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d99fdc8-1d01-11e8-b83a-f77571edd31c\",\"url\":\"http://private.media.yangcong345.com/high/high_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"pc\",\"format\":\"mp4\",\"clarity\":\"high\"},{\"id\":\"3d9aa9e4-1d01-11e8-b83a-fb1a26a4b9c6\",\"url\":\"http://private.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.mp4\",\"platform\":\"mobile\",\"format\":\"mp4\",\"clarity\":\"middle\"},{\"id\":\"3d9aab06-1d01-11e8-b83a-9b6806dd87f7\",\"url\":\"https://hls.media.yangcong345.com/mobileL/mobileL_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"low\"},{\"id\":\"3d9aac00-1d01-11e8-b83a-ef51f704b087\",\"url\":\"https://hls.media.yangcong345.com/mobileM/mobileM_5a9773b127e2c04e2d38177f_fragment.m3u8\",\"platform\":\"mobile\",\"format\":\"hls\",\"clarity\":\"middle\"}],\"duration\":\"91.799\"}";

    @Test
    public void test() {
        JSONObject j1 = JSONObject.parseObject(jons1);
        JSONObject j2 = JSONObject.parseObject(jons2);

        System.out.println();


//       JSONObject j22=fastJsonDiff.sortJsonObject(j2);
        List list = null;
        String s = fastJsonDiff.compareJson(j1, j2, null, list);
        System.out.println("s:" + s);
    }


    @Test
    public void test2() {
        String[][] data = {{"1", "2", "aa"}, {"1", "2", "aa"}};

        PageData pagedata = new PageData();
        pagedata.setData(data);
        pagedata.setDraw(2);
        pagedata.setRecordsTotal(10);
        pagedata.setRecordsTotal(10);

        System.out.println("data11:" + JSONArray.toJSONString(data));
        String peopleJson = JSON.toJSONString(pagedata);
        System.out.println(JSON.toJSONString(pagedata));
        System.out.println(pagedata.toString());

    }


}
