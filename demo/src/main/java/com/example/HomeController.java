package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = "/")//{[path:[^\\.]*}
    public String redirect() {
        // 前端路由交由 Angular 处理
        return "forward:/index.html";
    }
}