package com.example.vccorp_task7.model;



import java.util.Objects;

public class User {
    private Integer id;
    private String name;
    private String address;
    private Integer age;

    private Long money;


    // Default constructor
    public User() {
    }

    // Constructor with all fields
    public User(Integer id, String name, String address, Integer age, long money) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.money = money;
    }

    // Constructor without id (useful for creating new users)
    public User(String name, String address, Integer age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
    // Validate method
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                address != null && !address.trim().isEmpty() &&
                age != null && age > 1 && age < 100;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }

    // equals and hashCode methods for comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(address, user.address) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, age);
    }
}