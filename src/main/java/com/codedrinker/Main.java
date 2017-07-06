/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codedrinker;

import com.alibaba.fastjson.JSON;
import com.codedrinker.dao.ActivityDao;
import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.DBException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@SpringBootApplication
@ComponentScan
public class Main {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping("/")
    String index() {
        return "index";
    }


    @RequestMapping("/authorization")
    @ResponseBody
    Object authorization(@RequestParam String code) {
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


    @RequestMapping("/activity/new")
    @ResponseBody
    Object newActivity(@RequestBody Activity activity) {
        try {
            activityDao.save(activity);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok(activity);
    }

    @RequestMapping("/activity/{userId}")
    @ResponseBody
    Object activities(@PathVariable(value = "userId") String userId) {
        List<Activity> activities;
        try {
            activities = activityDao.listByUserId(userId);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.ok(activities);
    }
}
