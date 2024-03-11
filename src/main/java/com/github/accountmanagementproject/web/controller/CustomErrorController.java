package com.github.accountmanagementproject.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
//@Getter
//public class CustomErrorController implements ErrorController {
//    private static final String ERROR_PATH = "/error";
//
////    @RequestMapping(ERROR_PATH)
////    public String handleError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
////        HttpSession s  = httpServletRequest.getSession();
////        int sa = httpServletResponse.getStatus();
////        return "뽀숑";
////    }
//
//}
