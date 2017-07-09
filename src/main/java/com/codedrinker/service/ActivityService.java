package com.codedrinker.service;

import com.codedrinker.dao.ActivityDao;
import com.codedrinker.dao.ParticipatorDao;
import com.codedrinker.dao.UserDao;
import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Participator;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.entity.User;
import com.codedrinker.exception.DBException;
import com.codedrinker.utils.LogUtils;
import org.apache.commons.lang3.StringUtils;
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
            LogUtils.log("new activity", activity);
            activityDao.save(activity);
            Participator participator = new Participator();
            participator.setUserId(activity.getUser().getId());
            participator.setActivityId(activity.getId());
            participatorDao.save(participator);
            User user = userDao.getById(activity.getUser().getId());
            if (user == null) {
                userDao.save(activity.getUser());
            }
            return ResponseDTO.ok(activity);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        } catch (Exception e) {
            return ResponseDTO.error(e.getMessage());
        }
    }

    public ResponseDTO attend(Participator participator) {
        try {
            List<Participator> participators = participatorDao.listByActivityId(participator.getActivityId());
            for (Participator dbParticipator : participators) {
                if (dbParticipator.getUserId() == participator.getUserId()) {
                    return ResponseDTO.exist();
                }
            }
            participatorDao.save(participator);
            User user = userDao.getById(participator.getUserId());
            if (user == null) {
                userDao.save(participator.getUser());
            }
            return ResponseDTO.ok(participator);
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        }
    }

    public ResponseDTO listByUserId(String uid) {
        List<Activity> activities;
        try {
            List<Participator> participators = participatorDao.listByUserId(uid);

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

    public ResponseDTO getById(String id, String uid) {
        try {
            Activity activity = activityDao.getById(id);
            LogUtils.log("activity", activity);
            if (activity != null) {
                List<Participator> participators = participatorDao.listByActivityId(activity.getId());
                LogUtils.log("participators", participators);
                List<String> ids = new ArrayList<>();
                for (Participator participator : participators) {
                    ids.add(participator.getUserId());
                }
                activity.setOwner(StringUtils.equals(uid, activity.getUser().getId()));
                activity.setAttended(ids.contains(uid));
                activity.setUncommitted(!activity.isAttended());
                LogUtils.log("ids", ids);
                LogUtils.log("uid", uid);
                List<User> users = userDao.listByIds(ids);
                activity.setParticipators(users);

                for (User user : users) {
                    if (StringUtils.equals(user.getId(), activity.getUser().getId())) {
                        activity.setUser(user);
                        break;
                    }
                }

                return ResponseDTO.ok(activity);
            } else {
                return ResponseDTO.notFound();
            }
        } catch (DBException e) {
            return ResponseDTO.error(e.getMessage());
        }
    }
}
