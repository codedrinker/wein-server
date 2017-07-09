package com.codedrinker.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 01/07/2017.
 */

public class Activity {
    private String id;
    private Integer kind;
    private String title;
    private String description;
    private String date;
    private String time;
    private Location location;
    private User user;
    private boolean isOwner;
    private boolean isAttended;
    private boolean uncommitted;

    public boolean isUncommitted() {
        return uncommitted;
    }

    public void setUncommitted(boolean uncommitted) {
        this.uncommitted = uncommitted;
    }

    public boolean isAttended() {
        return isAttended;
    }

    public void setAttended(boolean attended) {
        isAttended = attended;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    List<User> participators = new ArrayList<>();

    public List<User> getParticipators() {
        return participators;
    }

    public void setParticipators(List<User> participators) {
        this.participators = participators;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", kind=" + kind +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", location=" + location +
                ", user=" + user +
                '}';
    }
}
