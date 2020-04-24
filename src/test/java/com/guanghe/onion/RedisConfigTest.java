package com.guanghe.onion;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        redisTemplate.delete("hashValue");
        redisTemplate.opsForHash().put("hashValue", "map1", "map1-1");
        redisTemplate.opsForHash().put("hashValue", "map1", "map1-1");
    }


    @Test
    public void test2() throws Exception {
        redisTemplate.delete("hashValue");
        System.out.println("start:" + redisTemplate.opsForHash().entries("hashValue"));

        redisTemplate.opsForHash().put("hashValue", "map1", "map1-1");
        redisTemplate.opsForHash().put("hashValue", "map2", "map2-2");

        List<Object> hashList = redisTemplate.opsForHash().values("hashValue");
        System.out.println("通过values(H key)方法获取变量中的hashMap值:" + hashList);

        Map<Object, Object> map = redisTemplate.opsForHash().entries("hashValue");
        System.out.println("通过entries(H key)方法获取变量中的键值对:" + map);

        Map newMap = new HashMap();
        newMap.put("map3", "map3-3");
        newMap.put("map5", "map5-5");
        redisTemplate.opsForHash().putAll("hashValue", newMap);
        redisTemplate.opsForHash().put("hashValue", "map77", "map7-7");
        System.out.println("newMap:" + newMap.hashCode());
        System.out.println("map:" + redisTemplate.opsForHash().entries("hashValue").hashCode());
        newMap = redisTemplate.opsForHash().entries("hashValue");
        newMap.put("map99", "map9-9");
//        redisTemplate.opsForHash().putAll("hashValue",newMap);

        map = redisTemplate.opsForHash().entries("hashValue");

        System.out.println("final:" + map);



     /*   Map<String, Object> testMap = new HashMap();
        testMap.put("name", "666");
        testMap.put("age", 27);
        testMap.put("class", "1");
        redisTemplate.opsForHash().putAll("redisHash", testMap);
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        redisTemplate.opsForHash().put("redisHash", "name", "666");
        redisTemplate.opsForHash().put("redisHash", "age", 26);
        redisTemplate.opsForHash().put("redisHash", "class", "6");
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        System.out.println(redisTemplate.opsForHash().values("redisHash"));

        System.out.println(redisTemplate.opsForHash().entries("redisHash"));


*/


//        Map map2=new HashMap();
//        map2.put("1","aa");
//        redisTemplate.opsForHash().putAll("ren",map2);
//        redisTemplate.opsForHash().entries("ren").put("2","bb");
//
//       System.out.println(redisTemplate.opsForHash().entries("ren").toString());

    }

    @Test
    public void testObj() throws Exception {

//        https://blog.csdn.net/javaxiaibai0414/article/details/88666453
        redisTemplate.opsForValue().set("num", "123");
        redisTemplate.opsForValue().get("num");

        redisTemplate.opsForValue().set("num", "123", 10, TimeUnit.SECONDS);
        redisTemplate.opsForValue().get("num");//设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null

        //覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        redisTemplate.opsForValue().set("key", "hello world");
        redisTemplate.opsForValue().set("key", "redis", 6);
        System.out.println("***************" + redisTemplate.opsForValue().get("key"));

        //设置键的字符串值并返回其旧值
        redisTemplate.opsForValue().set("getSetTest", "test");
        System.out.println(redisTemplate.opsForValue().getAndSet("getSetTest", "test2"));

        //如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET

        redisTemplate.opsForValue().append("test", "Hello");
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.opsForValue().append("test", "world");
        System.out.println(redisTemplate.opsForValue().get("test"));

        redisTemplate.opsForValue().set("key", "hello world");
        System.out.println("***************" + redisTemplate.opsForValue().size("key"));

        //返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0。当key存储的值不是列表时返回错误
        System.out.println(redisTemplate.opsForList().size("list"));

        //返回的结果为推送操作后的列表的长度
        redisTemplate.opsForList().leftPush("list", "java");
        redisTemplate.opsForList().leftPush("list", "python");
        redisTemplate.opsForList().leftPush("list", "c++");

//            批量把一个数组插入到列表中
        String[] strs = new String[]{"1", "2", "3"};
        redisTemplate.opsForList().leftPushAll("list", strs);
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
        //[3, 2, 1]

        redisTemplate.opsForList().rightPush("listRight", "java");
        redisTemplate.opsForList().rightPush("listRight", "python");
        redisTemplate.opsForList().rightPush("listRight", "c++");


        String[] strs0 = new String[]{"1", "2", "3"};
        redisTemplate.opsForList().rightPushAll("list", strs0);
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
        //[1, 2, 3]

        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));
        redisTemplate.opsForList().set("listRight", 1, "setValue");
        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));
        //[java, python, oc, c++]
        //[java, setValue, oc, c++]

//            从存储在键中的列表中删除等于值的元素的第一个计数事件。
//            计数参数以下列方式影响操作：
//            count> 0：删除等于从头到尾移动的值的元素。
//            count <0：删除等于从尾到头移动的值的元素。
//            count = 0：删除等于value的所有元素。
        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));
        redisTemplate.opsForList().remove("listRight", 1, "setValue");//将删除列表中存储的列表中第一次次出现的“setValue”。
        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));

//            根据下表获取列表中的值，下标是从0开始的
        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));
        System.out.println(redisTemplate.opsForList().index("listRight", 2));

//            弹出最左边的元素，弹出之后该值在列表中将不复存在

        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
        System.out.println(redisTemplate.opsForList().leftPop("list"));
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));

//            弹出最右边的元素，弹出之后该值在列表中将不复存在
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
        System.out.println(redisTemplate.opsForList().rightPop("list"));
        System.out.println(redisTemplate.opsForList().range("list", 0, -1));

//            删除给定的哈希hashKeys
        System.out.println(redisTemplate.opsForHash().delete("redisHash", "name"));
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        System.out.println(redisTemplate.opsForHash().hasKey("redisHash", "666"));
        System.out.println(redisTemplate.opsForHash().hasKey("redisHash", "777"));

        System.out.println(redisTemplate.opsForHash().get("redisHash", "age"));

        System.out.println(redisTemplate.opsForHash().keys("redisHash"));


        System.out.println(redisTemplate.opsForHash().size("redisHash"));
        Map<String, Object> testMap = new HashMap();
        testMap.put("name", "666");
        testMap.put("age", 27);
        testMap.put("class", "1");
        redisTemplate.opsForHash().putAll("redisHash1", testMap);
        System.out.println(redisTemplate.opsForHash().entries("redisHash1"));

        redisTemplate.opsForHash().put("redisHash", "name", "666");
        redisTemplate.opsForHash().put("redisHash", "age", 26);
        redisTemplate.opsForHash().put("redisHash", "class", "6");
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        System.out.println(redisTemplate.opsForHash().values("redisHash"));

        System.out.println(redisTemplate.opsForHash().entries("redisHash"));

        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan("redisHash", ScanOptions.scanOptions().build());
        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

//            无序集合中添加元素，返回添加个数
//            也可以直接在add里面添加多个值 如：redisTemplate.opsForSet().add("setTest","aaa","bbb")
        String[] strs3 = new String[]{"str1", "str2"};
        System.out.println(redisTemplate.opsForSet().add("setTest", strs3));

        String[] strs2 = new String[]{"str1", "str2"};
        System.out.println(redisTemplate.opsForSet().remove("setTest", strs2));
        System.out.println(redisTemplate.opsForSet().pop("setTest"));
        System.out.println(redisTemplate.opsForSet().members("setTest"));
        redisTemplate.opsForSet().move("setTest", "aaa", "setTest2");
        System.out.println(redisTemplate.opsForSet().members("setTest"));
        System.out.println(redisTemplate.opsForSet().members("setTest2"));

        System.out.println(redisTemplate.opsForSet().size("setTest"));

        System.out.println(redisTemplate.opsForSet().members("setTest"));
        Cursor<Object> curosr2 = redisTemplate.opsForSet().scan("setTest", ScanOptions.NONE);
        while (curosr2.hasNext()) {
            System.out.println(curosr2.next());
        }


//            新增一个有序集合，存在的话为false，不存在的话为true

        System.out.println(redisTemplate.opsForZSet().add("zset1", "zset-1", 1.0));
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<>("zset-5", 9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<>("zset-6", 9.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        System.out.println(redisTemplate.opsForZSet().add("zset1", tuples));
        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));


        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));
        System.out.println(redisTemplate.opsForZSet().remove("zset1", "zset-6"));
        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));

        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));
        System.out.println(redisTemplate.opsForZSet().rank("zset1", "zset-2"));

        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1", 0, 5));
        System.out.println(redisTemplate.opsForZSet().count("zset1", 0, 5));
        System.out.println(redisTemplate.opsForZSet().size("zset1"));
        System.out.println(redisTemplate.opsForZSet().score("zset1", "zset-1"));
//            移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().range("zset2", 0, -1));
        System.out.println(redisTemplate.opsForZSet().removeRange("zset2", 1, 2));
        System.out.println(redisTemplate.opsForZSet().range("zset2", 0, -1));
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("zzset1", ScanOptions.NONE);
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }


    }


}