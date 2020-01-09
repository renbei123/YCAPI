package com.guanghe.onion;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guanghe.onion.tools.fastJsonDiff;

import java.util.Iterator;
import java.util.List;

public class fastJsonDiff11 {


    private final static String st1 = "";
    private final static String st2 = "";

    @SuppressWarnings("unchecked")
    public static void compareJson(JSONObject json1, JSONObject json2, String key) {
        Iterator<String> i = json1.keySet().iterator();
        while (i.hasNext()) {
            key = i.next();
            if (!json2.containsKey(key)) {
                System.err.println("不一致key:" + key + ",json1有该key， json2没有");
                continue;
            }
            compareJson(json1.get(key), json2.get(key), key);
        }
//        return sb.toString();
    }

    public static void compareJson(Object json1, Object json2, String key) {
        if (json1 instanceof JSONObject) {
//            System.out.println("this JSONObject----" + key);
            compareJson((JSONObject) json1, (JSONObject) json2, key);
        } else if (json1 instanceof JSONArray) {
//            System.out.println("this JSONArray----" + key);
            compareJson(fastJsonDiff2.sortJsonArray((JSONArray) json1), fastJsonDiff2.sortJsonArray((JSONArray) json2), key);
//            compareJson((JSONArray) json1, (JSONArray) json2, key);

        } else if (json1 instanceof String) {
//            System.out.println("this String----" + key);
//            compareJson((String) json1, (String) json2, key);
            try {
                String json1ToStr = json1.toString();
                String json2ToStr = json2.toString();
                compareJson(json1ToStr, json2ToStr, key);
            } catch (Exception e) {
                System.err.println("转换发生异常 key:" + key);
                e.printStackTrace();
            }

        } else if (json1 == null) {

        } else {
//            System.err.println("this other----" + key);
            compareJson(json1.toString(), json2.toString(), key);
        }
    }

    public static void compareJson(String str1, String str2, String key) {
     /*   System.out.println("compare --- key:" + key);
        System.out.println("json1:" + str1 );
        System.out.println("json2:" + str2);*/
        if (!str1.equals(str2)) {
//            sb.append("key:"+key+ ",json1:"+ str1 +",json2:"+str2);
            System.err.println("不一致key:" + key + ",json1:" + str1 + ",json2:" + str2);
        } else {
            // System.out.println("一致：key:" + key + ",json1:" + str1 + ",json2:" + str2);
        }
    }

    public static void compareJson(JSONArray json1, JSONArray json2, String key) {
        if (json1 != null && json2 != null) {
            Iterator i1 = json1.iterator();
            Iterator i2 = json2.iterator();
            while (i1.hasNext()) {
                compareJson(i1.next(), i2.next(), key);
            }
        } else {
            if (json1 == null && json2 == null) {
                System.err.println("不一致：key:" + key + "  在json1和json2中均不存在");
            } else if (json1 == null) {
                System.err.println("不一致：key:" + key + "  在json1中不存在");
            } else if (json2 == null) {
                System.err.println("不一致：key:" + key + "  在json2中不存在");
            } else {
                System.err.println("不一致：key:" + key + "  未知原因");
            }

        }
    }

    public static void main(String[] args) {


        String jons1 = "{\n" +
                "    \"name\": \"19-2.2.14 一次函数的上下平移\",\n" +
                "    \"scenes\": [\n" +
                "        \"chapter\"\n" +
                "    ],\n" +
                "    \"videos\": [\n" +
                "        {\n" +
                "            \"name\": \"图象的上下平移\",\n" +
                "            \"id\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "            \"thumbnail\": \"https://course.yangcong345.com/%E4%B8%80%E6%AC%A1%E5%87%BD%E6%95%B0_10aI_%E4%B8%80%E6%AC%A1%E5%87%BD%E6%95%B0%E7%9A%84%E4%B8%8A%E4%B8%8B%E5%B9%B3%E7%A7%BB.bmp\",\n" +
                "            \"description\": \"\",\n" +
                "            \"order\": 0,\n" +
                "            \"type\": \"course\",\n" +
                "            \"difficulty\": 1,\n" +
                "            \"publishers\": [\n" +
                "                0\n" +
                "            ],\n" +
                "            \"interactions\": [\n" +
                "                {\n" +
                "                    \"id\": \"84345c6c-555e-11e7-94ef-939624ea145b\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 34.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"D\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"8435ab94-555e-11e7-94f0-eb4f79191a05\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 271.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"8436feb8-555e-11e7-94f1-172b92a359e4\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 293.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"clips\": [],\n" +
                "            \"url\": \"http://private.media.yangcong345.com/pcM/pcM_586fd4e8065b7e9d7142974f.mp4?e=1557977582&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:6312p5xPVUPvj9Gh4dnIZ11gbFo=\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"skills\": [\n" +
                "        {\n" +
                "            \"name\": \"代数几何转化\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"problems\": [\n" +
                "        {\n" +
                "            \"id\": \"03ade896-57e9-11e7-bd40-9336782febc1\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=2x-4$的图象向上平移$3$个单位后，新图象的解析式为 （  ）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x+3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=5x-4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x-7$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x-1$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=2x-4$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $3$个单位<br> 则$y=2x-4$<span style=\\\"color:#1696ea;\\\"> $+3$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=2x-1$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b05cb6-57e9-11e7-bd41-eb990aec9ce4\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=3x-2$的图象向上平移$5$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3x+3$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=8x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3x-7$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=3x-2$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $5$个单位<br> 则$y=3x-2$<span style=\\\"color:#1696ea;\\\"> $+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=3x+3$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b32ee6-57e9-11e7-bd42-0b53eb24822d\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-x-2$的图象向上平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x-1$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x-3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-x-2$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $1$个单位<br> 则$y=-x-2$<span style=\\\"color:#1696ea;\\\"> $+1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x-1$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b6c31c-57e9-11e7-bd43-67b2ab1843e3\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-2x-1$的图象向下平移$3$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-4$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-5x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x+2$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-2x-1$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$3$个单位<br> 则$y=-2x-1$<span style=\\\"color:#1696ea;\\\"> $-3$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-2x-4$<br> <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b96e50-57e9-11e7-bd44-4fe8ae94483e\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-3x-1$的图象向下平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-4x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-3x-2$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-3x$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-3x-1$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$1$个单位<br> 则$y=-3x-1$<span style=\\\"color:#1696ea;\\\"> $-1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-3x-2$<br>  <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03bd6050-57e9-11e7-bd45-53b6d6ec4427\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-x+3$的图象向下平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x+4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x+3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x+2$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-x+3$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$1$个单位<br> 则$y=-x+3$<span style=\\\"color:#1696ea;\\\"> $-1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x+2$<br>  <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c0e9fa-57e9-11e7-bd46-c7d48a5d2c75\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=3x-1$的图象向____平移____个单位后，可得到$y=3x-5$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$4$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=3x-1$<span style=\\\"color:#1696ea;\\\"> $-4$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=3x-5$<br> $ \\\\therefore $ 把一次函数$y=3x-1$的图象<span style=\\\"color:#f1572e;\\\">向下平移$4$个单位</span>后，可得到$y=3x-5$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c3adde-57e9-11e7-bd47-7bb126808215\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=2x-1$的图象向____平移____个单位后，可得到$y=2x+4$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$5$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$5$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=2x-1$<span style=\\\"color:#1696ea;\\\">$+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=2x+4$<br> $ \\\\therefore $ 把一次函数$y=2x-1$的图象<span style=\\\"color:#f1572e;\\\">向上平移$5$个单位</span>后，可得到$y=2x+4$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c76d48-57e9-11e7-bd48-2b5356013822\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-x+4$的图象向____平移____个单位后，可得到$y=-x-2$的图象 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$6$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-x+4$<span style=\\\"color:#1696ea;\\\">$-6$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x-2$<br> $ \\\\therefore $ 把一次函数$y=-x+4$的图象<span style=\\\"color:#f1572e;\\\">向下平移$6$个单位</span>后，可得到$y=-x-2$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03cb4d32-57e9-11e7-bd49-cf07963677f8\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-x-3$的图象向____平移____个单位后，可得到$y=-x+5$的图象 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$8$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$8$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-x-3$<span style=\\\"color:#1696ea;\\\">$+8$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x+5$<br> $ \\\\therefore $ 把一次函数$y=-x-3$的图象<span style=\\\"color:#f1572e;\\\">向上平移$8$个单位</span>后，可得到$y=-x+5$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03cd9272-57e9-11e7-bd4a-07ff9d346204\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-3x-6$的图象向____平移____个单位后，可得到$y=-3x-1$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$5$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$5$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$7$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$7$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-3x-6$<span style=\\\"color:#1696ea;\\\">$+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-3x-1$<br> $ \\\\therefore $ 把一次函数$y=-3x-6$的图象<span style=\\\"color:#f1572e;\\\">向上平移$5$个单位</span>后，可得到$y=-3x-1$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"494dafa2-f439-11e7-8b0a-87bcf2ce8248\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"hybrid\",\n" +
                "            \"body\": \"函数$y=2x+3$的图象可以看做由直线$y=2x$向====平移____个单位长度而得到.\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"65\",\n" +
                "            \"frequency\": 10,\n" +
                "            \"useGoalVideo\": true,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"上\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"直线$y=kx$向<span style=\\\"color:#1696ea;\\\">上（下）</span>平移$b$个单位长度（$b>0$）<br>\\n $ \\\\to $ 直线$y=kx $<span style=\\\"color:#1696ea;\\\">$ \\\\pm $</span>$ b$<br>\\n<br>\\n如图所示\\n<div><img src=\\\"https://course.yangcong345.com/FoGuq_wlU8TzsUixkeN25qXGkF1M?imageView2/2/w/360\\\" /></div>\\n函数$y=2x+3$的图象可以看做由直线$y=2x$<br>\\n向上平移$3$个单位长度而得到\",\n" +
                "                    \"jump\": 0,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"videoId\": \"\",\n" +
                "                    \"videoName\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [\n" +
                "                \"上\",\n" +
                "                \"$3$\"\n" +
                "            ],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": null,\n" +
                "                \"level\": null,\n" +
                "                \"continuity\": null\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"relate\": {}\n" +
                "}";
        String jons2 = "{\n" +
                "    \"name\": \"19-2.2.14 一次函数的上下平移\",\n" +
                "    \"scenes\": [\n" +
                "        \"chapter\"\n" +
                "    ],\n" +
                "    \"videos\": [\n" +
                "        {\n" +
                "            \"name\": \"图象的上下平移\",\n" +
                "            \"id\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "            \"thumbnail\": \"https://course.yangcong345.com/%E4%B8%80%E6%AC%A1%E5%87%BD%E6%95%B0_10aI_%E4%B8%80%E6%AC%A1%E5%87%BD%E6%95%B0%E7%9A%84%E4%B8%8A%E4%B8%8B%E5%B9%B3%E7%A7%BB.bmp\",\n" +
                "            \"description\": \"\",\n" +
                "            \"order\": 0,\n" +
                "            \"type\": \"course\",\n" +
                "            \"difficulty\": 1,\n" +
                "            \"publishers\": [\n" +
                "                0\n" +
                "            ],\n" +
                "            \"interactions\": [\n" +
                "                {\n" +
                "                    \"id\": \"84345c6c-555e-11e7-94ef-939624ea145b\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 34.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"D\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"8435ab94-555e-11e7-94f0-eb4f79191a05\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 271.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"8436feb8-555e-11e7-94f1-172b92a359e4\",\n" +
                "                    \"videoId\": \"8432d982-555e-11e7-94ee-53c71468ec92\",\n" +
                "                    \"time\": 293.7,\n" +
                "                    \"jump\": 0,\n" +
                "                    \"choices\": [\n" +
                "                        {\n" +
                "                            \"body\": \"A\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"B\",\n" +
                "                            \"correct\": false,\n" +
                "                            \"jump\": null\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"body\": \"C\",\n" +
                "                            \"correct\": true,\n" +
                "                            \"jump\": null\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"clips\": [],\n" +
                "            \"url\": \"http://private.media.yangcong345.com/pcM/pcM_586fd4e8065b7e9d7142974f.mp4?e=1557977613&token=Ap-_6XBBOas4n-w-osaYig82pVft-v63Rdu_2lPV:mc6_wxvrJXC3B8rEmK4lgvspbzU=\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"skills\": [\n" +
                "        {\n" +
                "            \"name\": \"代数几何转化\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"problems\": [\n" +
                "        {\n" +
                "            \"id\": \"03ade896-57e9-11e7-bd40-9336782febc1\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=2x-4$的图象向上平移$3$个单位后，新图象的解析式为 （  ）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x+3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=5x-4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x-7$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=2x-1$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=2x-4$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $3$个单位<br> 则$y=2x-4$<span style=\\\"color:#1696ea;\\\"> $+3$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=2x-1$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b05cb6-57e9-11e7-bd41-eb990aec9ce4\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=3x-2$的图象向上平移$5$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3x+3$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=8x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3x-7$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=3x-2$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $5$个单位<br> 则$y=3x-2$<span style=\\\"color:#1696ea;\\\"> $+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=3x+3$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b32ee6-57e9-11e7-bd42-0b53eb24822d\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-x-2$的图象向上平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x-1$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x-3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-2$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-x-2$的图象<span style=\\\"color:#1696ea;\\\">向上平移</span> $1$个单位<br> 则$y=-x-2$<span style=\\\"color:#1696ea;\\\"> $+1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x-1$<br> <span style=\\\"color:#f1572e;\\\">注：图象向上平移了多少个单位，$b$就加多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 1,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b6c31c-57e9-11e7-bd43-67b2ab1843e3\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-2x-1$的图象向下平移$3$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-4$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-5x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x+2$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-2x-1$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$3$个单位<br> 则$y=-2x-1$<span style=\\\"color:#1696ea;\\\"> $-3$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-2x-4$<br> <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03b96e50-57e9-11e7-bd44-4fe8ae94483e\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-3x-1$的图象向下平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-4x-1$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-3x-2$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-3x$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-3x-1$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$1$个单位<br> 则$y=-3x-1$<span style=\\\"color:#1696ea;\\\"> $-1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-3x-2$<br>  <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03bd6050-57e9-11e7-bd45-53b6d6ec4427\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"practice\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"一次函数$y=-x+3$的图象向下平移$1$个单位后，新图象的解析式为 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"55\",\n" +
                "            \"frequency\": 0,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x+4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-2x+3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=-x+2$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"$y=3$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"一次函数$y=-x+3$的图象<span style=\\\"color:#1696ea;\\\">向下平移</span>$1$个单位<br> 则$y=-x+3$<span style=\\\"color:#1696ea;\\\"> $-1$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x+2$<br>  <span style=\\\"color:#f1572e;\\\">注：图象向下平移了多少个单位，$b$就减多少</span>\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"step\",\n" +
                "                \"level\": 2,\n" +
                "                \"continuity\": true\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c0e9fa-57e9-11e7-bd46-c7d48a5d2c75\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=3x-1$的图象向____平移____个单位后，可得到$y=3x-5$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$4$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=3x-1$<span style=\\\"color:#1696ea;\\\"> $-4$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=3x-5$<br> $ \\\\therefore $ 把一次函数$y=3x-1$的图象<span style=\\\"color:#f1572e;\\\">向下平移$4$个单位</span>后，可得到$y=3x-5$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c3adde-57e9-11e7-bd47-7bb126808215\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=2x-1$的图象向____平移____个单位后，可得到$y=2x+4$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$3$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$5$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$5$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=2x-1$<span style=\\\"color:#1696ea;\\\">$+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=2x+4$<br> $ \\\\therefore $ 把一次函数$y=2x-1$的图象<span style=\\\"color:#f1572e;\\\">向上平移$5$个单位</span>后，可得到$y=2x+4$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03c76d48-57e9-11e7-bd48-2b5356013822\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-x+4$的图象向____平移____个单位后，可得到$y=-x-2$的图象 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$4$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$6$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$6$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-x+4$<span style=\\\"color:#1696ea;\\\">$-6$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x-2$<br> $ \\\\therefore $ 把一次函数$y=-x+4$的图象<span style=\\\"color:#f1572e;\\\">向下平移$6$个单位</span>后，可得到$y=-x-2$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03cb4d32-57e9-11e7-bd49-cf07963677f8\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-x-3$的图象向____平移____个单位后，可得到$y=-x+5$的图象 （）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$2$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$8$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$8$\",\n" +
                "                        \"correct\": true\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-x-3$<span style=\\\"color:#1696ea;\\\">$+8$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-x+5$<br> $ \\\\therefore $ 把一次函数$y=-x-3$的图象<span style=\\\"color:#f1572e;\\\">向上平移$8$个单位</span>后，可得到$y=-x+5$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"03cd9272-57e9-11e7-bd4a-07ff9d346204\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"single_choice\",\n" +
                "            \"body\": \"将一次函数$y=-3x-6$的图象向____平移____个单位后，可得到$y=-3x-1$的图象（）\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"75\",\n" +
                "            \"frequency\": 100,\n" +
                "            \"useGoalVideo\": false,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"下，$5$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$5$\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下，$7$\",\n" +
                "                        \"correct\": false\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"上，$7$\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"$ \\\\because y=-3x-6$<span style=\\\"color:#1696ea;\\\">$+5$</span><br> $\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:\\\\:=-3x-1$<br> $ \\\\therefore $ 把一次函数$y=-3x-6$的图象<span style=\\\"color:#f1572e;\\\">向上平移$5$个单位</span>后，可得到$y=-3x-1$的图象\",\n" +
                "                    \"title\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": \"goal\",\n" +
                "                \"level\": 3,\n" +
                "                \"continuity\": false\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"494dafa2-f439-11e7-8b0a-87bcf2ce8248\",\n" +
                "            \"goalId\": \"a3c03eb8-53f4-11e7-8a3b-336a72fe3c91\",\n" +
                "            \"purpose\": \"exam\",\n" +
                "            \"type\": \"hybrid\",\n" +
                "            \"body\": \"函数$y=2x+3$的图象可以看做由直线$y=2x$向====平移____个单位长度而得到.\",\n" +
                "            \"source\": \"\",\n" +
                "            \"difficulty\": \"65\",\n" +
                "            \"frequency\": 10,\n" +
                "            \"useGoalVideo\": true,\n" +
                "            \"choices\": [\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"body\": \"上\",\n" +
                "                        \"correct\": true\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"body\": \"下\",\n" +
                "                        \"correct\": false\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            \"explains\": [\n" +
                "                {\n" +
                "                    \"body\": \"直线$y=kx$向<span style=\\\"color:#1696ea;\\\">上（下）</span>平移$b$个单位长度（$b>0$）<br>\\n $ \\\\to $ 直线$y=kx $<span style=\\\"color:#1696ea;\\\">$ \\\\pm $</span>$ b$<br>\\n<br>\\n如图所示\\n<div><img src=\\\"https://course.yangcong345.com/FoGuq_wlU8TzsUixkeN25qXGkF1M?imageView2/2/w/360\\\" /></div>\\n函数$y=2x+3$的图象可以看做由直线$y=2x$<br>\\n向上平移$3$个单位长度而得到\",\n" +
                "                    \"jump\": 0,\n" +
                "                    \"title\": \"\",\n" +
                "                    \"videoId\": \"\",\n" +
                "                    \"videoName\": \"\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"blanks\": [\n" +
                "                \"上\",\n" +
                "                \"$3$\"\n" +
                "            ],\n" +
                "            \"prompts\": [],\n" +
                "            \"passed\": true,\n" +
                "            \"practiceProblem\": {\n" +
                "                \"type\": null,\n" +
                "                \"level\": null,\n" +
                "                \"continuity\": null\n" +
                "            },\n" +
                "            \"hasCopy\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"relate\": {}\n" +
                "}";

        JSONObject jsonObject1 = JSONObject.parseObject(jons1);
        JSONObject jsonObject2 = JSONObject.parseObject(jons2);
//        JSONObject jsonObject11 =fastJsonDiff2.sortJsonObject(jsonObject1);
//        JSONObject jsonObject22 =fastJsonDiff2.sortJsonObject(jsonObject2);
//        System.out.println(JSONObject.toJSONString(jsonObject11, SerializerFeature.WriteMapNullValue));
//        System.out.println(jsonObject22.toString());
        List list = null;
        String s = fastJsonDiff.compareJson(jsonObject1, jsonObject2, null, list);
        System.out.println(s);
    }

}