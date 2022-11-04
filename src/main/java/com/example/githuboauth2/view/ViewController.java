package com.example.githuboauth2.view;

// 성공 화면을 보여줄 Controller

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/social")
    public String socialSuccess(Model model,
                                @RequestParam(value = "provider", required = false) String provider,
                                @RequestParam(value = "oauthId", required = false) String oauthId
    ){
        model.addAttribute("provider", provider);
        model.addAttribute("oauthId", oauthId);
        return "social-success";
    }
}
