package com.TouristNest.travelGuide.controller;

import com.TouristNest.travelGuide.JPArepository.UserDataRepository;
import com.TouristNest.travelGuide.Model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Objects;


@Controller
public class MainController{

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private ImplementService userService;

    @GetMapping("/homepage")
    public String homepage(){
        return "Homepage";
    }


@PostMapping("/signupPage")
public String createUser(@ModelAttribute @NotNull User user,
                         @RequestParam String name,
                         @RequestParam String email, RedirectAttributes redirectAttributes, Model model){

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
                                //userService.createUser(user);
                                System.out.println("Successfully saved!");
                                redirectAttributes.addFlashAttribute("successMessage",
                                        "Successfully registered!");
                                model.addAttribute("user", user);
                                return "redirect:/signup";
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
                        return "redirect:/signup";
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
    return "redirect:/signup";
}


    //get the email from database and check the valid email
    @RequestMapping(value = "/loginpage", method = RequestMethod.GET)
    public String checkEmailAvailability(@RequestParam String email, RedirectAttributes redirectAttributes) {
       try{
           boolean isEmailRegistered = userService.existsByEmail(email);

           if(!Objects.equals(email,"")){
               if(isEmailRegistered){
                   return "redirect:/homepage";
               }else{
                   redirectAttributes.addFlashAttribute("successMessage",
                           "This email is not registered!");
                   return "redirect:/signin";
               }
           }else{
               redirectAttributes.addFlashAttribute("successMessage",
                       "Enter Email and Password!");
               return "redirect:/signin";
           }
       }catch (Exception e){
           System.out.println("Error: "+e);
       }
       return "redirect:/signin";
    }
    @RequestMapping("/signin")
    public String signin(){
        return "SignIn";
    }
    @RequestMapping("/signup")
    public String signup(){
        return "SignUp";
    }
    @RequestMapping("/berlin")
    public String berlin(){
        return "Berlin";
    }
    @RequestMapping()
    public String SignUpErrorHandle(){
        return "SignUpErrorHandle.html";
    }




























    //    @RequestMapping(value = "/addUserDataToDBMS", method = RequestMethod.POST)
//    public String createAccount(@RequestParam String name,
//                                @RequestParam String email,
//                                @RequestParam int number,
//                                @RequestParam String password,
//                                @RequestParam String confirm_password, Model model){
//
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setNumber(number);
//        user.setPassword(password);
//        user.setPassword(confirm_password);
//
//        if(Objects.equals(password, confirm_password)){
//            userDataRepository.save(user);
//            System.out.println("Saves!");
//            return "redirect:/signin";
//        }else{
//            System.out.println("Something error");
//        }
//        return "";
//    }

}
