package com.example.vccorp_task7.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleSqlException(DataAccessException ex, Model model) {
        model.addAttribute("error", "Đã xảy ra lỗi khi truy cập cơ sở dữ liệu: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex, Model model) {
        model.addAttribute("error", "Đã xảy ra lỗi null pointer: " + ex.getMessage());
        return "error";
    }
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
