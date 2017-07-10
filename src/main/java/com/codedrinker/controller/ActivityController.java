package com.codedrinker.controller;

import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.Participator;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.service.ActivityService;
import com.codedrinker.service.AuthorizationService;
import com.codedrinker.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private AuthorizationService authorizationService;


    @RequestMapping(value = "/activity/new", method = RequestMethod.POST)
    @ResponseBody
    Object newActivity(@RequestBody Activity activity) {
        if (activity.getUser() != null && StringUtils.isBlank(activity.getUser().getId())) {
            ResponseDTO authorize = authorizationService.authorize(activity.getUser().getCode());
            if (authorize.isOK()) {
                activity.getUser().setId(((Authorization) authorize.getData()).getOpenid());
            } else {
                return ResponseDTO.unauthorized();
            }
        }
        ResponseDTO responseDTO = activityService.save(activity);
        return responseDTO;
    }

    @RequestMapping(value = "/activity/attend", method = RequestMethod.POST)
    @ResponseBody
    Object attendActivity(@RequestBody Participator participator) {
        LogUtils.log("participator", participator);
        ResponseDTO attend = activityService.attend(participator);
        return attend;
    }

    @RequestMapping(value = "/activity/show", method = RequestMethod.GET)
    @ResponseBody
    Object showActivity(@RequestParam(name = "id") String id, @RequestParam(name = "uid") String uid) {
        ResponseDTO activity = activityService.getById(id, uid);
        return activity;
    }

    @RequestMapping(value = "/activities/participators", method = RequestMethod.GET)
    @ResponseBody
    Object participators(@RequestParam(name = "id") String id, @RequestParam(name = "uid") String uid) {
        ResponseDTO participators = activityService.getParticipators(id, uid);
        return participators;
    }

    @RequestMapping("/activities")
    @ResponseBody
    Object activities(@RequestParam(name = "uid", required = false, defaultValue = "") String uid) {
        return activityService.listByUserId(uid);
    }
}
