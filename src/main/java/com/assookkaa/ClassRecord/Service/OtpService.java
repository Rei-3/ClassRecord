package com.assookkaa.ClassRecord.Service;

import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import com.assookkaa.ClassRecord.Utils.ApiException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

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

    @Async
    public CompletableFuture<Boolean> sendOtpEmail (String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String otp = generateOtp();
                User user = userRepository.findByEmail(email);

                if (user != null) {
                    user.setOtp(otp);
                    userRepository.save(user);
                } else {
                    System.out.println("USER NOT FOUND");
                    return false;
                }

                String subject = "Your OTP";
                String body = "Your OTP Code: " + otp;
                emailService.sendEmail(email, subject, body);

                return true;
            } catch (ApiException e) {
                throw new ApiException("Failed to Send OTP", 69420, "OTP FAILURE");

            }
        });
    }

    public boolean validateOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }


}
