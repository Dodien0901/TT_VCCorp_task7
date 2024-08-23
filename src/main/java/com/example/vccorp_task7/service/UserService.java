package com.example.vccorp_task7.service;

import com.example.vccorp_task7.dao.UserDao;
import com.example.vccorp_task7.dao.UserDaoImpl;
import com.example.vccorp_task7.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDao userDao = new UserDaoImpl();

    public void addUser(User user) throws SQLException {
        if (user.getName().isEmpty() || user.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Name and address cannot be empty");
        }
        if (user.getAge() <= 1 || user.getAge() >= 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100");
        }
        if (userDao.findById(user.getId()) != null) {
            throw new IllegalArgumentException("User with this ID already exists");
        }
        userDao.addUser(user);
    }

    public void deleteUser(int id) throws SQLException {
        if (userDao.findById(id) == null) {
            throw new IllegalArgumentException("User not found");
        }
        userDao.deleteUser(id);
    }

    public void updateUser(User user) throws SQLException {
        if (user.getName().isEmpty() || user.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Name and address cannot be empty");
        }
        if (user.getAge() <= 1 || user.getAge() >= 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100");
        }
        if (userDao.findById(user.getId()) == null) {
            throw new IllegalArgumentException("User not found");
        }
        userDao.updateUser(user);
    }

    public User findById(int id) throws SQLException {
        return userDao.findById(id);
    }

    public List<User> findByName(String name) throws SQLException {
        return userDao.findByName(name);
    }

    public List<User> findByAddress(String address) throws SQLException {
        return userDao.findByAddress(address);
    }

    public List<User> findAllSortedByName() throws SQLException {
        return userDao.getAllUsers(); // Already sorted by name in the DAO
    }
}