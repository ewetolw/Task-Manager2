package com.pd.eweltol.taskManager2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {
        @RequestMapping("/user")
        @ResponseBody
        public Principal user(Principal user) {
            return user;
        }

}