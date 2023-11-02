package com.TouristNest.travelGuide.controller;

import com.TouristNest.travelGuide.JPArepository.UserDataRepository;
import com.TouristNest.travelGuide.JPArepository.UserService;
import com.TouristNest.travelGuide.Model.User;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Controller
public class ImplementService implements UserService {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public User createUser(User user) {
        return userDataRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean emailExists = userDataRepository.existsById(email);
        if (emailExists) {
            return true;
        }
        return false;
    }
    @Override
    public String sendOTPToUser(String userEmail) {
        String otp = EmailService.generateOTP();
        emailService.sendOTP(userEmail, otp);
        System.out.println(otp);
        return otp;
    }

    @RequestMapping("/otpvarification")
    public String verifyOTP(@RequestParam String otp, @NotNull HttpSession session, RedirectAttributes redirectAttributes) {
        String storedOTP = (String) session.getAttribute("otp");

        if (storedOTP != null && storedOTP.equals(otp)) {
            User user = (User) session.getAttribute("user");
            System.out.println("2ndD controller");
            createUser(user);
            session.removeAttribute("user");
            session.removeAttribute("otp");
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");
            return "redirect:/signup";
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Invalid OTP. Please try again.");
            return "redirect:/otpserver";
        }
    }

}
