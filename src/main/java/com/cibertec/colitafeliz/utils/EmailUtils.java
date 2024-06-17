package com.cibertec.colitafeliz.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> ccList) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        //helper.setFrom("i201822031@cibertec.edu.pe");
        helper.setFrom("carlos.encarnacion@adexperu.org.pe");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // true indicates HTML content

        if (ccList != null && !ccList.isEmpty()) {
            helper.setCc(ccList.toArray(new String[0])); // Setting CC recipients
        }

        mailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList) {
        String[] ccArray = new String[ccList.size()];
        ccList.forEach(cc -> {
            ccArray[ccList.indexOf(cc)] = cc;
        });
        return ccArray;
    }

}
