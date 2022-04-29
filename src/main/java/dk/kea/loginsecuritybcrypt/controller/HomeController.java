package dk.kea.loginsecuritybcrypt.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // BCrypt https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
    private final String username = "test@test.dk";
    private final String password = "hemmeligt";
    private final String hpassword = BCrypt.hashpw(password,BCrypt.gensalt());

    @GetMapping("/")
    public String index(HttpSession session, Model model){
        String uid = (String) session.getAttribute("uid");
        String pwd = (String) session.getAttribute("pwd");

        if(username.equals(uid) && pwd.equals(hpassword)){
            model.addAttribute("login", true);
            model.addAttribute("user", uid);
        }else{
            model.addAttribute("login", false);
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String uid, @RequestParam String pwd, HttpSession session, Model model){

        //uid og pwd indtastet?
        if ((uid.length()>0) && (pwd.length()>0)){
            if(username.equals(uid) && BCrypt.checkpw(pwd, hpassword)) {
                //login ok - gem credentials i cookie
                session.setAttribute("uid", uid);
                //snyder lidt og gemmer det eksisterende hashede password
                session.setAttribute("pwd", hpassword);
            }else{
                //login forkert - slet credentials fra cookie
                session.setAttribute("uid", null);
                session.setAttribute("pwd", null);
            }
        }
        return "redirect:/";
    }
}
