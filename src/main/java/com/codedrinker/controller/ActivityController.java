package com.codedrinker.controller;

import com.codedrinker.dao.ActivityDao;
import com.codedrinker.entity.Activity;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Controller
public class ActivityController {

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping("/activity/new")
    @ResponseBody
    Object newActivity(@RequestBody Activity activity) {
        try {
            System.out.println(activity);
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
