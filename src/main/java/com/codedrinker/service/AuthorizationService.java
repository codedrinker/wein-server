package com.codedrinker.service;

import com.alibaba.fastjson.JSON;
import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.ResponseDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by codedrinker on 08/07/2017.
 */
@Component
public class AuthorizationService {
    public ResponseDTO authorize(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxf41c765f273813bf&secret=0f4a4cfe067f089536991218039dcac0&js_code=%s&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, code))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                Authorization authorization = JSON.parseObject(execute.body().string(), Authorization.class);
                return ResponseDTO.ok(authorization);
            } else {
                return ResponseDTO.error(execute.message());
            }

        } catch (IOException e) {
            ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.error("Unknown");
    }
}
