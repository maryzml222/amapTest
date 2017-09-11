package com.example.mylibrary.http.request;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequestParams {

    private Map<String, String> requestParamsMap = null;

    public HttpRequestParams() {
        requestParamsMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });

    }

    public Map<String, String> getRequestParamsMap() {
        return requestParamsMap;
    }

    @Override
    public String toString() {
        return "HttpRequestParams{" +
                "requestParamsMap=" + requestParamsMap.toString() +
                '}';
    }
}
