package com.TouristNest.travelGuide.controller;

import com.TouristNest.travelGuide.JPArepository.BookingRepo;
import com.TouristNest.travelGuide.JPArepository.UserDataRepository;
import com.TouristNest.travelGuide.Model.Booking;
import com.TouristNest.travelGuide.Model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.thymeleaf.model.IModel;

import java.security.Principal;
import java.util.Objects;

@Controller
public class MainController{

    private String Storeemail;
    private User userID;

    @Autowired
    private ImplementService userService;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private EmailService emailService;

    @GetMapping("/touristNest")
    public String homepage(Principal principal, Model model){
        if(principal != null){
            //email = principal.getName();
            //User user = userDataRepository.findByEmail(email);
            model.addAttribute("user", userID);
            return "Homepage";
        }else{
            return "Homepage";
        }
    }
    @RequestMapping(value = "/touristNest/signup", method = RequestMethod.POST)
    public String createUser(@ModelAttribute @NotNull User user,
                             RedirectAttributes redirectAttributes,
                             Model model, HttpSession session){

        //boolean passWordStrength = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&!])[A-Za-z\\d@#$%^&!]{6,}$");
        boolean hasUppercase = user.getPassword().matches(".*[A-Z].*");
        //boolean hasLowercase = user.getPassword().matches(".*[a-z].*");
        //boolean hasDigit = user.getPassword().matches(".*\\d.*");
        boolean hasSpecialChar = user.getPassword().matches(".*[@#$%^&+=].*");

        try{
            boolean isEmailRegistered = userService.existsByEmail(user.getEmail());
            if(!isEmailRegistered){
                if(Objects.equals(user.getPassword(),user.getConfirm_password())){
                    if(!Objects.equals(user.getPassword(), "")){
                        if(hasUppercase && hasSpecialChar){
                            if(user.getPassword().length() >= 6){
                                //model.addAttribute("user", user);
                                String otp = userService.sendOTPToUser(user.getEmail(), user.getName());
                                System.out.println("SEND:"+otp);
                                // Store user data and OTP in the session
                                session.setAttribute("user", user);
                                session.setAttribute("otp", otp);
                                redirectAttributes.addFlashAttribute("successMessage",
                                        "An OTP have been send on your Email.");
                                return "redirect:/touristNest/signup/otpserver";
                            }else{
                                //redirectAttributes.addFlashAttribute("successMessage",
                                //"Password length at least 6!");
                                model.addAttribute("user", user);
                                model.addAttribute("successMessage", "Password length at least 6!");
                                return "SignUpErrorHandle.html";
                            }
                        }else{
                            //redirectAttributes.addFlashAttribute("successMessage",
                            //"Password must contain 1[A-Z] & 1 Symbol!");
                            model.addAttribute("user", user);
                            model.addAttribute("successMessage", "Password must contain 1[A-Z] & 1 Symbol!");
                            return "SignUpErrorHandle.html";
                        }
                    }else{
                        model.addAttribute("user", user);
                        redirectAttributes.addFlashAttribute("successMessage", "Please enter password!");
                        //return "SignUpErrorHandle.html";
                        return "redirect:/touristNest/signup";
                    }
                }else{
                    //redirectAttributes.addFlashAttribute("successMessage",
                    //"Password Doesn't Matched!");
                    model.addAttribute("user", user);
                    model.addAttribute("successMessage", "Password don't matched!");
                    return "SignUpErrorHandle.html";
                }
            }else{
                redirectAttributes.addFlashAttribute("successMessage",
                        "This email is already registered!");
            }
        }catch (Exception e){
            System.out.println("Error: "+e);
        }
        return "redirect:/touristNest/signup";
    }

    @RequestMapping("/signin")
    public String signin(Principal principal, Model model){
        if(principal != null){
            Storeemail = principal.getName();
            userID = userDataRepository.findByEmail(Storeemail);
            model.addAttribute("user", userID);
            return "SignIn";
        }else{
            return "SignIn";
        }
    }
    @RequestMapping("/touristNest/signup")
    public String signup(Principal principal, Model model){
        if(principal != null){
            model.addAttribute("user", userID);
            return "SignUp";
        }else{
            return "SignUp";
        }
    }
    @RequestMapping("/touristNest/berlin")
    public String berlin(Principal principal, Model model){
        if(principal != null){
            model.addAttribute("user", userID);
            return "Berlin";
        }else{
            return "Berlin";
        }
    }
    @RequestMapping
    public String SignUpErrorHandle(){
        return "SignUpErrorHandle";
    }
    @RequestMapping("/touristNest/signup/otpserver")
    public String otpServer(){
        return "OTPServer";
    }

    @RequestMapping("/user/profile")
    public String userProfile(Principal principal, Model model){
        if(principal != null){
            Storeemail = principal.getName();
            userID = userDataRepository.findByEmail(Storeemail);
            model.addAttribute("user", userID);
            return "UserProfile";
        }else{
            return "UserProfile";
        }
    }
    @RequestMapping("/user/home")
    public String userProfiles(){
        return "home";
    }

    @RequestMapping("/hotel/SpittelmarktBerlin")
    public String SpittelmarktBerlin(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("user", userID);
            return "SpittelmarktBerlin";
        }else{
            return "SpittelmarktBerlin";
        }
    }
    @PostMapping("/SpittelmarktBerlin")
    public String processForm(@RequestParam(required = false) String email22,
                              @RequestParam String email,
                              RedirectAttributes redirectAttributes,
                              Authentication authentication,
                              @ModelAttribute @Valid Booking booking,
                              @RequestParam String cardNumber,
                              BindingResult bindingResult) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // If there are validation errors, handle them appropriately
            redirectAttributes.addFlashAttribute("successMessage", "Invalid input, please check the form");
            return "redirect:/hotel/SpittelmarktBerlin";
        }
        User user = userDataRepository.findByEmail(email);
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("successMessage", "Sign in first to confirm booking!");
            return "redirect:/hotel/SpittelmarktBerlin";
        } else if (!Objects.equals(Storeemail,email)) {
            redirectAttributes.addFlashAttribute("successMessage", "Please! Use your Registered Email !");
            return "redirect:/hotel/SpittelmarktBerlin";
        }else if(cardNumber.length() != 16){
            System.out.println(cardNumber.length());
            redirectAttributes.addFlashAttribute("successMessage", "Invalid Card Number!");
            return "redirect:/hotel/SpittelmarktBerlin";
        } else {
            booking.setUser(user);
            bookingRepo.save(booking);
            redirectAttributes.addFlashAttribute("successMessage", "Submission success!");
            emailService.bookingConfirm(email, user.getName(), booking.getCheckIN(), booking.getCheckOUT(), booking.getNumberOfPersons());
            return "redirect:/hotel/SpittelmarktBerlin";
        }
    }

}
