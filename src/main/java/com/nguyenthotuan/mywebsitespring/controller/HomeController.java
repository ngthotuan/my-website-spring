package com.nguyenthotuan.mywebsitespring.controller;

import com.nguyenthotuan.mywebsitespring.config.AppProperties;
import com.nguyenthotuan.mywebsitespring.domain.ContactMessage;
import com.nguyenthotuan.mywebsitespring.domain.Subscriber;
import com.nguyenthotuan.mywebsitespring.domain.User;
import com.nguyenthotuan.mywebsitespring.model.MailDto;
import com.nguyenthotuan.mywebsitespring.model.UserLoginDto;
import com.nguyenthotuan.mywebsitespring.model.UserRegisterDto;
import com.nguyenthotuan.mywebsitespring.service.ContactMessageService;
import com.nguyenthotuan.mywebsitespring.service.EmailSenderService;
import com.nguyenthotuan.mywebsitespring.service.SubscriberService;
import com.nguyenthotuan.mywebsitespring.service.UserService;
import com.nguyenthotuan.mywebsitespring.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserService userService;
    private final HttpSession session;
    private final EmailSenderService emailSenderService;
    private final AppProperties appProperties;
    private final SubscriberService subscriberService;
    private final ContactMessageService contactMessageService;

    @Value("${spring.mail.username}")
    private String from;

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
        session.setAttribute("role", user.getRole());
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

    @PostMapping("subscribe")
    public ResponseEntity<?> postSubscribe(HttpServletRequest request, @RequestParam String email) {
        try {
            Subscriber subscriber = subscriberService.findByEmail(email);
            if (subscriber != null) {
                return ResponseEntity.ok("Email đã đăng ký");
            }
            subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriber.setIp(HttpUtil.getRequestIP(request));
            subscriber.setTime(LocalDateTime.now());
            subscriberService.saveSubscriber(subscriber);
            Map<String, Object> properties = new HashMap<>();
            properties.put("appProps", appProperties);
            properties.put("email", email);
            properties.put("time", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
            MailDto mailDto = MailDto.builder()
                    .from(String.format("%s <%s>", appProperties.getEmailSenderName(), from))
                    .to(email)
                    .subject("Đăng ký nhận tin")
                    .template("email/welcome")
                    .props(properties)
                    .build();
            emailSenderService.sendEmail(mailDto);
            return ResponseEntity.ok("Đăng ký thành công");
        } catch (Exception e) {
            log.error("postSubscribe error: ", e);
            return ResponseEntity.badRequest().body("Đăng ký thất bại");
        }
    }

    @PostMapping("contact")
    public ResponseEntity<?> postContact(HttpServletRequest request,
                                         @RequestParam String name,
                                         @RequestParam String email,
                                         @RequestParam String subject,
                                         @RequestParam String message
                                         ) {
        try {
            ContactMessage contactMessage = new ContactMessage();
            contactMessage.setName(name);
            contactMessage.setEmail(email);
            contactMessage.setSubject(subject);
            contactMessage.setMessage(message);
            contactMessage.setIp(HttpUtil.getRequestIP(request));
            contactMessage.setTime(LocalDateTime.now());
            contactMessageService.saveContactMessage(contactMessage);

            Map<String, Object> properties = new HashMap<>();
            properties.put("appProps", appProperties);
            properties.put("contactMessage", contactMessage);
            MailDto mailDto = MailDto.builder()
                    .from(String.format("%s <%s>", appProperties.getEmailSenderName(), from))
                    .to(email)
                    .subject("Rep: " + subject)
                    .template("email/reply")
                    .props(properties)
                    .build();
            emailSenderService.sendEmail(mailDto);
            return ResponseEntity.ok("Gửi tin nhắn thành công");
        } catch (Exception e) {
            log.error("postContact error: ", e);
            return ResponseEntity.badRequest().body("Có lỗi xảy ra");
        }
    }
}
