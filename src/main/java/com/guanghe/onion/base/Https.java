package com.guanghe.onion.base;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Https {


    public static Response send(String path, String method, Map headers, String body) {

        if (method.equalsIgnoreCase("GET")) {
            return given()
                    .headers(headers)
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
            if (isnull(body))
                return given()
                        .headers(headers)
                        .delete(path);
            else
                return given()
                        .headers(headers)
                        .body(body)
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


    public static boolean isnull(Object s) {
        return s == null || s.toString().trim().equals("");
    }
}
