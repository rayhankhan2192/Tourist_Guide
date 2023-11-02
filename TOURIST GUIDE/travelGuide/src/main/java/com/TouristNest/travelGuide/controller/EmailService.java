package com.TouristNest.travelGuide.controller;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOTP(String to,String name, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("TouristNest <clay.world.max.1011@gmail.com>");
            helper.setSubject("Your OTP");
            //helper.setText("Dear, "+name +"!");
            String emailContent = "Dear, "+name+" !\nWELCOME TO TOURIST NEST!\n" +
                    "This is a one-time generated password to verify your email.\n\n" +
                    "Your OTP is: " + otp + "\n\n" +
                    "Do not share your OTP with others!\n" +
                    "\n\nThis is a computer-generated email. Please do not reply!";

            helper.setText(emailContent);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
