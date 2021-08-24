package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.domain.User;
import com.nguyenthotuan.mywebsitespring.model.UserLoginDto;
import com.nguyenthotuan.mywebsitespring.model.UserRegisterDto;
import com.nguyenthotuan.mywebsitespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping
    public String getHomePage() {
        return "home/index";
    }

    @GetMapping("contact")
    public String getContact() {
        return "home/contact";
    }

    @GetMapping("register")
    public String getRegister(Model model) {
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }
        UserRegisterDto dto = new UserRegisterDto();
        model.addAttribute("user", dto);
        return "home/register";
    }

    @PostMapping("register")
    public ModelAndView postRegister(ModelMap model,
                                     @Validated @ModelAttribute("user") UserRegisterDto dto,
                                     BindingResult result) {
        if (userService.findByEmail(dto.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user.email", "Email đã đăng ký với tài khoản khác");
        }
        if (result.hasErrors()) {
            return new ModelAndView("home/register");
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userService.save(user);
        model.addAttribute("message", "Đăng ký thành công, bạn có thể đăng nhập");
        return new ModelAndView("redirect:/login", model);
    }


    @GetMapping("login")
    public String getLogin(Model model, @RequestParam(required = false) String message) {
        if (session.getAttribute("username") != null) {
            return "redirect:/";
        }
        if (message != null) {
            model.addAttribute("message", message);
        }

        UserLoginDto dto = new UserLoginDto();
        model.addAttribute("user", dto);
        return "home/login";
    }

    @PostMapping("login")
    public String postLogin(Model model, @ModelAttribute("user") UserLoginDto dto) {


        User user = userService.login(dto.getEmail(), dto.getPassword());
        if (user == null) {
            model.addAttribute("message", "Sai tài khoản hoặc mật khẩu!");
            return "home/login";
        }

        Object ruri = session.getAttribute("redirect-uri");
        session.setAttribute("username", user.getEmail());
        session.setAttribute("name", user.getName());
        if (ruri != null) {
            session.removeAttribute("redirect-uri");
            return String.format("redirect:%s", ruri);
        }
        return "redirect:/";
    }

    @GetMapping("logout")
    public String getLogout() {
        session.removeAttribute("username");
        session.removeAttribute("name");
        return "redirect:/";
    }
}
