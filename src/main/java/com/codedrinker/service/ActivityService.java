package com.codedrinker.service;

import com.codedrinker.dao.ActivityDao;
import com.codedrinker.dao.ParticipatorDao;
import com.codedrinker.dao.UserDao;
import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Participator;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.entity.User;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 08/07/2017.
 */
@Component
public class ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ParticipatorDao participatorDao;

    @Autowired
    private UserDao userDao;

    public ResponseDTO save(Activity activity) {
        try {
            activityDao.save(activity);
            Participator participator = new Participator();
            participator.setUserId(activity.getUser().getId());
            participator.setActivityId(activity.getId());
            participatorDao.save(participator);
            return ResponseDTO.ok(activity);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResponseDTO.error(e.getMessage());
        }
    }

    public ResponseDTO attend(Participator participator) {
        try {
            participatorDao.save(participator);
            User user = userDao.getById(participator.getUserId());
            if (user == null) {
                userDao.save(user);
            }
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        }
        return ResponseDTO.error("Unknown");
    }

    public ResponseDTO listByUserId(String userId) {
        List<Activity> activities;
        try {
            List<Participator> participators = participatorDao.listByUserId(userId);

            List<String> ids = new ArrayList<>();
            for (Participator participator : participators) {
                ids.add(participator.getActivityId());
            }
            activities = activityDao.listByIds(ids);
            return ResponseDTO.ok(activities);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResponseDTO.error(e.getMessage());
        }
    }

    public ResponseDTO getById(String id) {
        try {
            Activity activity = activityDao.getById(id);
            if (activity != null) {
                List<Participator> participators = participatorDao.listByActivityId(activity.getId());

                List<String> ids = new ArrayList<>();
                for (Participator participator : participators) {
                    ids.add(participator.getUserId());
                }
                List<User> users = userDao.listByIds(ids);
                activity.setParticipators(users);
                return ResponseDTO.ok(activity);
            } else {
                return ResponseDTO.notFound();
            }
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        }
    }
}
