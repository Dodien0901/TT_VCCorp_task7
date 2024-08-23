package com.example.vccorp_task7.dao;

import com.example.vccorp_task7.exception.InsufficientFundsException;
import com.example.vccorp_task7.model.User;
import com.example.vccorp_task7.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users (name, address, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getAddress(), user.getAge());
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, address = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getAddress(), user.getAge(), user.getId());
    }

    @Override
    public List<User> findByAddress(String address) {
        String sql = "SELECT * FROM users WHERE address LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + address + "%"}, (rs, rowNum) ->
                new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age"), rs.getLong("money")));
    }

    @Override
    public List<User> findByName(String name) {
        String sql = "SELECT * FROM users WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, (rs, rowNum) ->
                new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age"), rs.getLong("money")));
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age"), rs.getLong("money")));
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getInt("age"), rs.getLong("money")));
    }

    @Override
    @Transactional
    public void addMoney(int userId, long amount) {
        String sql = "UPDATE users SET money = money + ? WHERE id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }

    @Override
    @Transactional
    public void transferMoney(int fromUserId, int toUserId, long amount) {
        String deductSql = "UPDATE users SET money = money - ? WHERE id = ? AND money >= ?";
        String addSql = "UPDATE users SET money = money + ? WHERE id = ?";

        int deductedRows = jdbcTemplate.update(deductSql, amount, fromUserId, amount);
        if (deductedRows == 0) {
            throw new InsufficientFundsException("Không đủ tiền để chuyển");
        }

        jdbcTemplate.update(addSql, amount, toUserId);
    }
}