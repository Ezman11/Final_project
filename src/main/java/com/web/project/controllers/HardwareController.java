package com.web.project.controllers;

import com.web.project.entities.Check_in;
import com.web.project.entities.User;
import com.web.project.repositories.BuildingRepository;
import com.web.project.repositories.Check_inRepository;
import com.web.project.repositories.UserRepository;
import com.web.project.services.BuildingService;
import com.web.project.services.EmailService;
import com.web.project.services.QRcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class HardwareController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private Check_inRepository check_inRepository;

    @GetMapping("/iOt/{bID}/{sID}/{temp}")
    public String Iot(@PathVariable String bID, @PathVariable String sID, @PathVariable float temp) {
        DecimalFormat df = new DecimalFormat("0.0");
        String t = df.format(temp);
        temp = Float.parseFloat(t);
        System.out.println(temp);
        Check_in checkIn = new Check_in();
        DateTimeFormatter id = DateTimeFormatter.ofPattern("MMddHHmmss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        checkIn.setId(Long.parseLong(id.format(now)));
        checkIn.setStudent_id(sID);
        checkIn.setBuilding_id(bID);
        checkIn.setTemperature(temp);
        checkIn.setTime(dtf.format(now));
        check_inRepository.save(checkIn);


        if (temp>37.5){
            User user = userRepository.findById(sID).get();
            if(user.getStatus().equalsIgnoreCase("Risk")||user.getStatus().equalsIgnoreCase("High Risk")){
                user.setStatus("High Risk");
                userRepository.save(user);
            }else{
                user.setStatus("Risk");
                userRepository.save(user);
            }
        }else {
            User user = userRepository.findById(sID).get();
            if(user.getStatus().equalsIgnoreCase("High Risk")){
                user.setStatus("Risk");
                userRepository.save(user);
            }else{
                user.setStatus("Low Risk");
                userRepository.save(user);
            }
        }
        System.out.println(checkIn);
        System.out.println("ID : " + bID + " Student ID : " + sID + " Temperature : " + temp + " C");
        return "success";
    }

}

