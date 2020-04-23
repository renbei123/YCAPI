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

        String path = "https://api-wx-test.yangcong345.com/primary_account/curriculum/getCurriculums";
        String method = "get";
        Map header = new HashMap();
        String body = "";
        Response r = send(path, method, header, body);
        System.out.println(r.body().asString());

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