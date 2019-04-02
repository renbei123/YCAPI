package com.guanghe.onion;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.CoreMatchers.hasItems;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class YcApiApplicationTests {
	@Test
	public void test() {
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		long time = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(time);
//		System.out.println("time:"+currentDate);
		java.util.Date time1 = new java.util.Date();
		//(2)
java.sql.Date time2 = new java.sql.Date(System.currentTimeMillis());
		//(3)
java.sql.Time time3 = new java.sql.Time(System.currentTimeMillis());
		//(4)
java.sql.Timestamp time4 = new java.sql.Timestamp(System.currentTimeMillis());
System.out.println(time4);
DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH：mm：ss 今年第ww周  第DD天");
System.out.println("北京时间：" + sdf.format(time1) + "\n");
System.out.println("北京时间：" + sdf.format(time2)+ "\n");
System.out.println("北京时间：" + sdf.format(time3)+ "\n");
System.out.println("北京时间：" + sdf.format(time4)+ "\n");
		System.out.println("北京时间：" + time3.toString()+ "\n");
	}

	@Test
	public void postHttpsTest3() {
		RestAssured.baseURI = "https://ios-api-v5-0.yangcong345.com";
//		useRelaxedHTTPSValidation();
//		RestAssured.port = 443;
//		RestAssured.urlEncodingEnabled = false;
		Map cook=null;
		Response response = given()
				.contentType("application/json")
				.cookies(cook)
				.expect()
//				.body("books.tags[2].title", hasItems("自动化", "自动化测试"))
				.when()
				//.body("UserName=XXXX&Password=XXXXX&CheckCode=&Remember=false&LoginCheckCode=7505")
				.get("/course-tree/subjects");

		int statusCode = response.getStatusCode();
		System.out.println("statusCode:" + statusCode);

		// 获取Response 的所有 headers 并输出
		Headers headers = response.getHeaders();
		System.out.println(headers.toString());

		// 获取Response中header名为Content-Type的值
		String contentType = response.getHeader("Content-Type");
		System.out.println("contentType:" + contentType);
		// 等同上面方法
//		System.out.println(headers.get("Content-Type"));
//
//		// 校验某个Header 是否存在
//		System.out.println(headers.hasHeaderWithName("fasdfaf"));
//		System.out.println(headers.hasHeaderWithName("Server"));
//		Map<String, String> cookiesMap = response.cookies();
//		for (String key : cookiesMap.keySet()) {
//			System.out.println(key + ":" + cookiesMap.get(key));
//		}
//
//		System.out.println(response.cookie("bid"));
		// 把Response 的body转成string类型

//		System.out.println(response.getBody().asString());
//
//		int count = response.jsonPath().getInt("count");
//		System.out.println("count:" + count);

//		// 获取所有的 subtitle
//		ArrayList<String> subtitles = response.jsonPath().get("books.subtitle");
//		for (int i = 0; i < subtitles.size(); i++) {
//			System.out.println(subtitles.get(i));
//		}

		response.print();
	}

	@Test
	public void postHttpsTest2() {
		Response response1 = given()
				.contentType("application/x-www-form-urlencoded")
				.body("UserName=XXXX&Password=XXXXX&CheckCode=&Remember=false&LoginCheckCode=7505")
				.post("http://XXXX.XXXX.com/Home/Login");

		response1.print();
		// 获取reponse中所有的cookies
		Map cookies = response1.getCookies();
//
//		Response response2 = given()
//				// 写入第一个请求的cookies
//				.cookies(cookies)
//				.contentType("application/x-www-form-urlencoded")
//				.param("ActionName", "")
//				.param("CurrentUserNo", "XXXXX")
//				.post("http://XXXX.XXXXX.com/Home/IsCurrentAccountValid");
//
//		response2.print();
	}


}
