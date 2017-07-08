package com.codedrinker.dao;

import com.codedrinker.db.DBDataSource;
import com.codedrinker.entity.User;
import com.codedrinker.exception.DBException;
import com.codedrinker.utils.LogUtils;
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
public class UserDao {

    @Autowired
    private DBDataSource dbDataSource;

    public void save(User user) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = dbDataSource.getInstance().getConnection();
            String sql = "insert into account (id,nickname,avatarurl,gender,province,city,country,utime,ctime) values (?,?,?,?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getNickName());
            pstmt.setString(3, user.getAvatarUrl());
            pstmt.setInt(4, user.getGender());
            pstmt.setString(5, user.getProvince());
            pstmt.setString(6, user.getCity());
            pstmt.setString(7, user.getCountry());
            pstmt.setLong(8, System.currentTimeMillis());
            pstmt.setLong(9, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (Exception e) {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new DBException(e.getMessage());

        } finally {
            try {
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DBException(e1.getMessage());
            }
        }
    }

    public List<User> listByIds(List<String> ids) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        List<User> users = new ArrayList<>();

        try {
            connection = dbDataSource.getInstance().getConnection();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ids.size(); i++) {
                builder.append("?,");
            }
            String sql = "select * from account where id in ("
                    + builder.deleteCharAt(builder.length() - 1).toString() + ")";

            LogUtils.log("sql", sql);
            pstmt = connection.prepareStatement(sql);
            int index = 1;
            for (String id : ids) {
                pstmt.setObject(index++, id); // or whatever it applies
            }
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setAvatarUrl(resultSet.getString("avatarurl"));
                user.setNickName(resultSet.getString("nickname"));
                user.setGender(resultSet.getInt("gender"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new DBException(e1.getMessage());
            }
            throw new DBException(e.getMessage());
        } finally {
            try {
                resultSet.close();
                pstmt.close();
                connection.close();
            } catch (SQLException e1) {
                throw new DBException(e1.getMessage());
            }
        }
        return users;
    }

    public User getById(String userId) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = dbDataSource.getInstance().getConnection();
            StringBuilder builder = new StringBuilder();
            String sql = "select * from account where id=?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userId); // or whatever it applies
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setAvatarUrl(resultSet.getString("avatarurl"));
                user.setNickName(resultSet.getString("nickname"));
                user.setGender(resultSet.getInt("gender"));
                return user;
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
