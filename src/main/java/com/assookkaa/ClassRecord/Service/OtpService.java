package com.assookkaa.ClassRecord.Service;

import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import com.assookkaa.ClassRecord.Utils.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Value("${mail.smtp.username}")
    String admin;

    private String generateOtp() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand  = new Random();
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return code.toString();
    }

    public Boolean sendOtpEmail (String email) {
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

                String html = Files.readString(Paths.get("src/main/resources/templates/otp.html"), StandardCharsets.UTF_8);

                String body = html.replace("{{OTP}}", otp);

                emailService.sendEmail(user.getEmail(), subject, body);

                return true;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

    public Boolean sendRequestForReset (String email) {
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
            String subject = "RESPONSE: ACCOUNT RESET";

            String html = Files.readString(Paths.get("src/main/resources/templates/reset-account.html"), StandardCharsets.UTF_8);

            String body = html.replace("{{OTP}}", otp)
                    .replace("{{user}}", user.getFname()+ " "+user.getLname());
            emailService.sendEmail(user.getEmail(), subject, body);

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean sendChangeRequest (String email) {

        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                System.out.println("USER NOT FOUND" + email);
                return false;
            }
            String subject = "REQUEST: ACCOUNT RESET";

            String html = Files.readString(Paths.get("src/main/resources/templates/reset-account.html"), StandardCharsets.UTF_8);

            String body = html.replace("{{OTP}}", "Requesting For An Account Reset")
                    .replace("{{user}}", user.getFname()+ " "+user.getLname());
            emailService.sendEmail(admin, subject, body);

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean sendStatusChangeRequest (String email, Integer tdId, String academicYear, String sem, String user)
    {
        try{
            User users = userRepository.findByEmail(email);
            if (users == null) {
                System.out.println("USER NOT FOUND" + email);
                return false;
            }

            String subject = "REQUEST: Teaching Load Status Change";

            String html = Files.readString(Paths.get("src/main/resources/templates/change-teaching-load-status.html"), StandardCharsets.UTF_8);

            String body = html.replace("{{TeachingLoadId}}", tdId.toString())
                    .replace("{{}}", academicYear)
                    .replace("{{academicYear}}", academicYear)
                    .replace("{{semName}}", sem)
                    .replace("{{user}}", user)
                    .replace("{{userEmail}}", users.getEmail());

            emailService.sendEmailReq(admin, subject, body, users.getEmail());
            return true;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



}
