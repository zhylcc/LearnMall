package com.learn.demo.mall.web.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/web/access")
public class WAccessController {

    @GetMapping("/toLogin")
    public ModelAndView toLogin() {
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
