package com.web.project.services;


import java.io.File;
import java.nio.file.Paths;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    QRcodeGenerator qRcodeGenerator;

    public String sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("nutto101z@gmail.com");
        message.setTo(email);
        message.setSubject("Test Subject");
        message.setText("Test Body");

        javaMailSender.send(message);

        return "Mail sent successfully";
    }

    public String sendOTP(String email,String otp , String ref) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ระบบยืนยันและติดตามตัวตนผ่านคิวอาร์โค้ด <nutto101z@gmail.com>");
            message.setTo(email);
            message.setSubject("รหัส OTP");
            message.setText("รหัส OTP ของท่านคือ: " + otp + " (Ref: " + ref + " )");
            javaMailSender.send(message);
            return "OTP sent successfully";

        } catch (Exception e) {
            System.out.println(e);
            return "OTP sent failed";
        }
    }

    public String sendEmailTo1Person(emailForm form) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            message.setFrom("ระบบยืนยันและติดตามตัวตนผ่านคิวอาร์โค้ด <nutto101z@gmail.com>");
            message.setSubject(form.getSubject());
            message.setText(form.getSubject());
            message.addRecipients(Message.RecipientType.BCC,form.getEmail());
            javaMailSender.send(message);
            return "Mail sent successfully";

        } catch (Exception e) {
            System.out.println(e);
            return "Mail sent failed";
        }
    }

    public String sendQR(String email,String id) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper =
                    new MimeMessageHelper(message,true);

            messageHelper.setFrom("ระบบยืนยันและติดตามตัวตนผ่านคิวอาร์โค้ด <nutto101z@gmail.com>");
            messageHelper.setTo(email);
            messageHelper.setSubject("QR code");
            messageHelper.setText("ท่านได้ลงทะเบียนเพื่อขอรับ QR code สำเร็จแล้ว!\n กรุณาเก็บ QR code นี้ไว้เพื่อยืนยันตัวตนเมื่อเข้าใช้อาคาร");

            File file = new File(qRcodeGenerator.QR(id));

            messageHelper.addAttachment(file.getName(), file);

            javaMailSender.send(message);

            file.delete();
            return "Mail sent successfully";

        } catch (Exception e) {
            System.out.println(e);
            return "Mail sent failed";
        }
    }
}
