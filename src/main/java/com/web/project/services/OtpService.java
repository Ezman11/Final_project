package com.web.project.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Random;

public class OtpService {

    private String ref;

    private String otp;

    private String encode;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String genOtp(String email) {
        DecimalFormat formatter = new DecimalFormat("000000");
        otp = formatter.format(new Random().nextInt(999999));
        int[] temp = {65, 97};
        ref ="";
        ref += (char) (new Random().nextInt(26) + temp[new Random().nextInt(2)]);
        ref += (char) (new Random().nextInt(26) + temp[new Random().nextInt(2)]);
        ref += (char) (new Random().nextInt(26) + temp[new Random().nextInt(2)]);
        ref += (char) (new Random().nextInt(26) + temp[new Random().nextInt(2)]);
        encode = encoder(otp);
        return otp;
    }

    private String encoder(String pass) {
        String encode = bCryptPasswordEncoder.encode(pass);
        return encode;
    }

    public String getRef() {
        return ref;
    }

    public String getEncode() {
        return encode;
    }

    public String getOtp() {
        return otp;
    }

    public boolean OtpMatch(String otp ,String encode) {
        return bCryptPasswordEncoder.matches(otp, encode);
    }
}
