package com.codedrinker.entity;

/**
 * Created by codedrinker on 08/07/2017.
 */
public class Participator {
    private Long utime;
    private Long ctime;
    private String userId;
    private String activityId;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUtime() {
        return utime;
    }

    public void setUtime(Long utime) {
        this.utime = utime;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "Participator{" +
                "utime=" + utime +
                ", ctime=" + ctime +
                ", userId='" + userId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", user=" + user +
                '}';
    }
}
