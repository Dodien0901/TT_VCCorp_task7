package com.example.vccorp_task7.controller;

import com.example.vccorp_task7.exception.InsufficientFundsException;
import com.example.vccorp_task7.model.User;
import com.example.vccorp_task7.service.UserService;


import com.example.vccorp_task7.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/add")
    public String showAddForm(Model model) {
        logger.debug("Entering showAddForm method");
        model.addAttribute("user", new User());
        return "userForm";
    }

    @Autowired
    private UserDao userDao;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        if (isValidUser(user)) {
            userDao.addUser(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute User user) {
        if (isValidUser(user)) {
            userDao.updateUser(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userDao.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam String keyword, @RequestParam String searchBy, Model model) {
        List<User> users;
        if ("name".equals(searchBy)) {
            users = userDao.findByName(keyword);
        } else if ("address".equals(searchBy)) {
            users = userDao.findByAddress(keyword);
        } else {
            users = userDao.getAllUsers();
        }
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/{id}/add-money")
    public String addMoney(@PathVariable int id, @RequestParam long amount, RedirectAttributes redirectAttributes) {
        try {
                userDao.addMoney(id, amount);
                redirectAttributes.addFlashAttribute("message", "Successfully added money");
        } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error adding money: " + e.getMessage());
        }
        return "redirect:/users/" + id + "/money";
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestParam int fromUserId,
                                    @RequestParam int toUserId,
                                    @RequestParam long amount,
                                    RedirectAttributes redirectAttributes) {
        try {
                userDao.transferMoney(fromUserId, toUserId, amount);
                redirectAttributes.addFlashAttribute("message", "Money transferred successfully");
        } catch (InsufficientFundsException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error transferring money: " + e.getMessage());
        }
        return "redirect:/users/" + fromUserId + "/money";
    }

    @GetMapping("/{id}/money")
    public String showMoneyManagement(@PathVariable int id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "money-management";
    }
    @GetMapping("/{id}/transfer")
    public String showTransferMoneyPage(@PathVariable int id, Model model) {
        User user = userDao.findById(id);
        model.addAttribute("user", user);
        return "transfer";
    }

    private boolean isValidUser(User user) {
        return user.getName() != null && !user.getName().isEmpty() &&
                user.getAddress() != null && !user.getAddress().isEmpty() &&
                user.getAge() > 1 && user.getAge() < 100;
    }
}