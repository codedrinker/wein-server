package com.codedrinker.dao;

import com.alibaba.fastjson.JSON;
import com.codedrinker.db.DBDataSource;
import com.codedrinker.entity.Activity;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            String sql = "insert into activity (id,kind,title,description,date,time,location,user_id) values (?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setInt(2, activity.getKind());
            pstmt.setString(3, activity.getTitle());
            pstmt.setString(4, activity.getDescription());
            pstmt.setString(5, activity.getDate());
            pstmt.setString(6, activity.getTime());
            pstmt.setString(7, JSON.toJSONString(activity.getLocation()));
            pstmt.setString(8, activity.getUser().getId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                throw new DBException(e1.getMessage());
            }

            throw new DBException(e.getMessage());

        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                throw new DBException(e1.getMessage());
            }
        }
    }

    public List<Activity> listByUserId(String userId) {
        return null;
    }
}
