package com.guanghe.onion;

import com.guanghe.onion.tools.StringUtil;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class RequestUtil {


    String path = "https://ios-api-v5-0.yangcong345.com/login";
    String header = "{Content-Type=application/json}";
    Map headers = (Map) StringUtil.StringToMap(header);
    String body = "{\n" +
            "    \"name\": \"13810264865\",\n" +
            "    \"password\":\"yc123456\",\n" +
            "    \"countryCode\": \"CN\",\n" +
            "    \"gps\":[39.9920768737793, 116.49544525146484]\n" +
            "}";

    @Test
    public void test2() {


    }

    @Test
    public void test() {
        Map<String, String> setParams = new HashMap<>();
        setParams.put("phone", "123");
        Map<String, String> header2 = new HashMap<>();
        header2.put("User-Agent", "");
//        header2.put("Accept","*/*");
//        header2.put("Cache-Control","no-cache");
//        header2.put("Host","ios-api-v5-0.yangcong345.com");
//        header2.put("accept-encoding","gzip, deflate");
//        header2.put("Connection","keep-alive");
        header2.put("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjViODUyY2MzZTc4ZjEwMDYzYTIyMTNkMiIsInJvbGUiOiJzdHVkZW50IiwiaWF0IjoxNTczMTE5NDk3LCJleHAiOjE1NzMyMDU4OTd9.-fUpxGtWxjUbK2TaSTNgOSuwCpTyXQZZlI8xBQLqruA");
//        SchedulerTask2 task2=new SchedulerTask2();
//        Response response=send(path,"GET", headers,body);
        Response response = given().headers(header2).params(setParams).when().get(path);
//        Response response=given().header("authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjViODUyY2MzZTc4ZjEwMDYzYTIyMTNkMiIsInJvbGUiOiJzdHVkZW50IiwiaWF0IjoxNTYxMzQ4NzYwLCJleHAiOjE1NjE0MzUxNjB9.q9l76rm1_ietdfA0l6gp87IkZqSOO4RBBDZpEwCFF-A").request(Method.GET,path);
//        List<String> bookTitles = from(response).getList("store.book.findAll { it.price < 10 }.title");
//        Map heads=new HashMap();
//        response.getBody().prettyPrint();
//        List<String> list = JsonPath.parse(response.getBody().asString()).read("$.[0]");
//        System.out.println("{}".substring(1,1));
        System.out.println(response.getStatusCode());
//        System.out.println("***********:"+JsonPath.parse(response.getBody().asString()).read("$[0].sections[0].subsections[0].themes[0].id"));
//        System.out.println("***********:"+JsonPath.parse(response.getBody().asString()).read("$[1].name"));
//        System.out.println("***********:"+JsonPath.parse(response.getBody().asString()).read("$.[0].sections[0].subsections[0].themes[0].id"));
//        System.out.println("***********:"+ JsonPath.parse(response.getBody().asString()).read("$.[0].sections.findAll { }.name"));
        response.prettyPrint();
        System.out.println();
//        System.out.println("***********:"+response.);
//        System.out.println("***********:"+response.getBody().print());


    }

    public Response send(String path, String method, Map headers, String body) {

        if (method.equalsIgnoreCase("GET")) {
            return given()
                    .headers(headers)
                    .when()
                    .get(path);
        }

        if (method.equalsIgnoreCase("POST")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .post(path);
        }
        if (method.equalsIgnoreCase("PUT")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .put(path);
        }
        if (method.equalsIgnoreCase("PATCH")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .patch(path);
        }
        if (method.equalsIgnoreCase("DELETE")) {
            return given()
                    .headers(headers)
//                    .body(body)
                    .delete(path);
        }
        if (method.equalsIgnoreCase("OPTIONS")) {
            return given()
                    .headers(headers)
                    .body(body)
                    .options(path);
        }
        return null;
    }


    public String replaceExcept(String content, String patten) {
        // "key":***,
        String Patten = patten + "[^,]*,";

        Pattern pattern = Pattern.compile(Patten);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            String name = m.group();
            System.out.println("....***:" + name);
        }
        return content;
    }


}