package com.codedrinker.dao;

import com.codedrinker.db.DBDataSource;
import com.codedrinker.entity.Participator;
import com.codedrinker.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codedrinker on 07/07/2017.
 */
@Repository
public class ParticipatorDao {

    @Autowired
    private DBDataSource dbDataSource;

    public void save(Participator participator) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "insert into participator (user_id,activity_id,utime,ctime) values (?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, participator.getUserId());
            pstmt.setString(2, participator.getActivityId());
            pstmt.setLong(3, System.currentTimeMillis());
            pstmt.setLong(4, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (Exception e) {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
            }
        }
    }

    public List<Participator> listByActivityId(String activityId) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        List<Participator> participators = new ArrayList<>();

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "select * from participator where activity_id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, activityId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Participator participator = new Participator();
                participator.setUserId(resultSet.getString("user_id"));
                participator.setActivityId(resultSet.getString("activity_id"));
                participators.add(participator);
            }
        } catch (Exception e) {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e2) {
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e2) {
            }
        }
        return participators;
    }

    public List<Participator> listByUserId(String userId) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        List<Participator> participators = new ArrayList<>();

        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "select * from participator where user_id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userId);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Participator participator = new Participator();
                participator.setUserId(resultSet.getString("user_id"));
                participator.setActivityId(resultSet.getString("activity_id"));
                participators.add(participator);
            }
        } catch (Exception e) {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e2) {
            }
            throw new DBException(e.getMessage());

        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e2) {
            }
        }
        return participators;
    }
}
