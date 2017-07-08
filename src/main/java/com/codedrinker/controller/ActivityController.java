package com.codedrinker.controller;

import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Authorization;
import com.codedrinker.entity.Participator;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.service.ActivityService;
import com.codedrinker.service.AuthorizationService;
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
        if (activity.getUser() != null && activity.getUser().getId() == null) {
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
        ResponseDTO attend = activityService.attend(participator);
        return attend;
    }

    @RequestMapping("/activity/{userId}")
    @ResponseBody
    Object activities(@PathVariable(value = "userId") String userId) {
        return activityService.listByUserId(userId);
    }
}
