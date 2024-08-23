-- Tạo database
CREATE DATABASE user_task4;

-- Sử dụng database vừa tạo
USE user_task4;

-- Tạo bảng users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    CHECK (age > 1 AND age < 100)
);