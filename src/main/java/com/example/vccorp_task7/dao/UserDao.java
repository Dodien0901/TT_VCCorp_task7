package com.example.vccorp_task7.dao;

import com.example.vccorp_task7.model.User;
import java.sql.SQLException;
import java.util.List;


public interface UserDao {
    void addUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
    User findById(int id);
    List<User> findByName(String name);
    List<User> findByAddress(String address);
    List<User> getAllUsers();
    void addMoney(int userId, long amount);
    void transferMoney(int fromUserId, int toUserId, long amount);
}