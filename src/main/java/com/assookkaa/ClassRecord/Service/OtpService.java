package com.assookkaa.ClassRecord.Service;

import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public OtpService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private String generateOtp() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand  = new Random();
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return code.toString();
    }


    public void sendOtpEmail (String email) {
        String otp = generateOtp();
        User user  = userRepository.findByEmail(email);
        if (user != null) {
            user.setOtp(otp);
        }

        String subject = "Your OTP Code";
        String body = "Your OTP Code: " + otp;
        emailService.sendEmail(email, subject, body);
    }

    public boolean validateOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }


}
