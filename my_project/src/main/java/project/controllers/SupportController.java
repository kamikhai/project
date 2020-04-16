package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.services.UserService;

@Controller
public class SupportController {
    @Autowired
    private UserService userService;

    @GetMapping("/support")
    public String getChatPage(Model model, Authentication authentication, @RequestParam(required = false, name = "userId") Long userId) {
        if (authentication != null){
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
                if (userId == null){
                    return "admin_support";
                } else {
                    model.addAttribute("userId", userId);
                    return "admin_support_chat";
                }
            } else {
                model.addAttribute("userId", userService.findByEmail(authentication.getName()).get().getId());
                return "support";
            }
        }
        return "redirect:/";
    }
}
