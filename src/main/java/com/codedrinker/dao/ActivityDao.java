package com.codedrinker.dao;

import com.alibaba.fastjson.JSON;
import com.codedrinker.db.DBDataSource;
import com.codedrinker.entity.Activity;
import com.codedrinker.entity.Location;
import com.codedrinker.entity.User;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Repository
public class ActivityDao {

    @Autowired
    private DBDataSource dbDataSource;

    public void save(Activity activity) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "insert into activity (id,kind,title,description,date,time,location,user_id,utime,ctime) values (?,?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            String id = UUID.randomUUID().toString();
            activity.setId(id);
            pstmt.setString(1, id);
            pstmt.setInt(2, activity.getKind() != null ? activity.getKind() : 0);
            pstmt.setString(3, activity.getTitle() != null ? activity.getTitle() : "");
            pstmt.setString(4, activity.getDescription() != null ? activity.getDescription() : "");
            pstmt.setString(5, activity.getDate() != null ? activity.getDate() : "");
            pstmt.setString(6, activity.getTime() != null ? activity.getTime() : "");
            pstmt.setString(7, JSON.toJSONString(activity.getLocation()));
            pstmt.setString(8, activity.getUser() != null && activity.getUser().getId() != null ? activity.getUser().getId() : "");
            pstmt.setLong(9, System.currentTimeMillis());
            pstmt.setLong(10, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            throw new DBException(e.getMessage());

        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<Activity> listByIds(List<String> ids) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        List<Activity> activities = new ArrayList<>();

        try {
            connection = dbDataSource.getInstance().getConnection();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                builder.append("?,");
            }
            String sql = "select * from activity where id in "
                    + builder.deleteCharAt(builder.length() - 1).toString();
            pstmt = connection.prepareStatement(sql);
            int index = 1;
            for (String id : ids) {
                pstmt.setObject(index++, id); // or whatever it applies
            }
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setId(resultSet.getString("id"));
                activity.setDate(resultSet.getString("date"));
                activity.setTitle(resultSet.getString("title"));
                activity.setDescription(resultSet.getString("description"));
                activity.setKind(resultSet.getInt("kind"));
                activity.setLocation(JSON.parseObject(resultSet.getString("location"), Location.class));
                activity.setTime(resultSet.getString("time"));
                User user = new User();
                user.setId(resultSet.getString("user_id"));
                activity.setUser(user);
                activities.add(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DBException(e1.getMessage());
            }
        }
        return activities;
    }

    public List<Activity> listByUserId(String userId) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        List<Activity> activities = new ArrayList<>();

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "select * from activity where user_id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setId(resultSet.getString("id"));
                activity.setDate(resultSet.getString("date"));
                activity.setTitle(resultSet.getString("title"));
                activity.setDescription(resultSet.getString("description"));
                activity.setKind(resultSet.getInt("kind"));
                activity.setLocation(JSON.parseObject(resultSet.getString("location"), Location.class));
                activity.setTime(resultSet.getString("time"));
                User user = new User();
                user.setId(resultSet.getString("user_id"));
                activity.setUser(user);
                activities.add(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DBException(e1.getMessage());
            }
        }
        return activities;
    }

    public Activity getById(String id) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "select * from activity where id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Activity activity = new Activity();
                activity.setId(resultSet.getString("id"));
                activity.setDate(resultSet.getString("date"));
                activity.setTitle(resultSet.getString("title"));
                activity.setDescription(resultSet.getString("description"));
                activity.setKind(resultSet.getInt("kind"));
                activity.setLocation(JSON.parseObject(resultSet.getString("location"), Location.class));
                activity.setTime(resultSet.getString("time"));
                User user = new User();
                user.setId(resultSet.getString("user_id"));
                activity.setUser(user);
                return activity;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
