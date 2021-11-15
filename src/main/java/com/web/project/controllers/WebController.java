package com.web.project.controllers;

import com.web.project.entities.Building;
import com.web.project.entities.Check_in;
import com.web.project.entities.Otp;
import com.web.project.entities.User;
import com.web.project.repositories.BuildingRepository;
import com.web.project.repositories.Check_inRepository;
import com.web.project.repositories.UserRepository;
import com.web.project.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes("tempUser")
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private Check_inRepository check_inRepository ;

    @Autowired
    private UserService userService;

    @Autowired
    private QRcodeGenerator QrGenerate;

    @Autowired
    EmailService emailService;

    @GetMapping("/getQr")
    public String getQrForm(Model model) {
        model.addAttribute("getQrForm", new getQrForm());
        return "getQR";
    }

    @PostMapping("/getQr")
    public String getQrSubmit(Model model , @Valid getQrForm form) {
        boolean foundEmail = userRepository.findByEmail(form.getEmail()).isPresent();
        boolean foundID = userRepository.findById(form.getId()).isPresent();
        if(foundEmail && foundID){
            if(userRepository.findById(form.getId()).get().getEmail().equalsIgnoreCase(form.getEmail())){
                model.addAttribute("notCorrect",false);
                emailService.sendQR(form.getEmail(),form.getId());
                return "successGetQR";
            }
            else {
                model.addAttribute("notCorrect",true);
                return "getQr";
            }
        }else {
            model.addAttribute("notCorrect",true);
            return "getQr";
        }
    }

    @GetMapping("/getQrSuccess")
    public String gertQrSuccess() {
            return "successGetQR";
    }

    @GetMapping("/addUser")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addSubmit(@Valid User user, BindingResult bindingResult , Model model) {
        if(bindingResult.hasErrors()) {
            return "addUser";
        }else {
            System.out.println(user);
            model.addAttribute("tempUser",user);
            return "redirect:/otp";
        }
    }

    @GetMapping("/otp")
    public String otpForm(Model model) {
        model.getAttribute("tempUser");
        User user = (User) model.getAttribute("tempUser");
        if(user==null){
            return "redirect:/addUser";
        }
        Otp otpModel = new Otp();
        OtpService otpService = new OtpService();
        otpService.genOtp(user.getEmail());
        System.out.println(user);
        System.out.println(otpService.getOtp());
        otpModel.setRef(otpService.getRef());
        otpModel.setPassword(otpService.getEncode());
        model.addAttribute("otp", otpModel);
        emailService.sendOTP(user.getEmail(),otpService.getOtp(),otpService.getRef());
        return "otp";
    }

    @PostMapping("/otp")
    public String addUserOtp(Model model,@Valid User tempUser, Otp otp,BindingResult bindingResult) {
        System.out.println(tempUser);
        OtpService otpService = new OtpService();
        boolean notMatch = !otpService.OtpMatch(otp.getOtp(),otp.getPassword());
        model.addAttribute("notMatch",notMatch);
        if(notMatch) {
            return "otp";
        }
        if(bindingResult.hasErrors()) {
            return "redirect:/addUser";
        }else {
            emailService.sendQR(tempUser.getEmail(),tempUser.getStudent_id());
            userService.save(tempUser);
            System.out.println("save success");
            return "success";
        }
    }


    @GetMapping(value = "delete/{id}")
    public String destroy(@PathVariable String id)  {
        System.out.println("Delete USER : " + id);
        userRepository.deleteById(id);
        return "redirect:/tbUser";
    }

    @GetMapping(value = "deleteCheckIn/{id}")
    public String deleteCheckIn(@PathVariable Long id)  {
        check_inRepository.deleteById(id);
        return "redirect:/tbCheckIn";
    }

    @GetMapping(value = "deleteBuilding/{id}")
    public String deleteBuilding(@PathVariable String id)  {
        buildingRepository.deleteById(id);
        return "redirect:/tbBuilding";
    }

    @GetMapping(value = "set/{status}/{id}")
    public String setStatus(@PathVariable String id,@PathVariable int status)  {
        User user = userRepository.findById(id).get();
        System.out.println(status);
        if(status==0){
            user.setStatus("Low Risk");
        }else if(status==1){
            user.setStatus("Risk");
        }else if(status==2){
            user.setStatus("High Risk");
        }
        System.out.println(user);
        userRepository.save(user);
        System.out.println(user);
        user.setStatus("");
        return "redirect:/tbUser";
    }

    @GetMapping("/tbUser")
    public String tbUser(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("items", users);
        System.out.println(users+"");
        return "tbl_User";
    }

    @GetMapping("/tbBuilding")
    public String tbBuilding(Model model) {
        List<Building> buildings = buildingRepository.findAll();
        model.addAttribute("items", buildings);
        model.addAttribute("addBuilding", new Building());
        System.out.println(buildings+"");
        return "tbl_Building";
    }

    @GetMapping("/tbCheckIn")
    public String tbCheckIn(Model model) {
        List<Check_in> check_in = check_inRepository.findAll();
        model.addAttribute("items", check_in);
        System.out.println(check_in+"");
        return "tbl_CheckIn";
    }

    @GetMapping("home")
    public String home(Model model) {
        List<Building> buildings = buildingRepository.findAll();
        model.addAttribute("buildings", buildings);
        return "index";
    }

    @GetMapping("")
    public String index(Model model) {
        List<Building> buildings = buildingRepository.findAll();
        model.addAttribute("buildings", buildings);
        return "index";
    }

    @GetMapping("/sendmail/{email}")
    public String sendMailForm(Model model,@PathVariable String email) {
        emailForm form = new emailForm();
        form.setEmail(email);
        model.addAttribute("form",form);
        return "sendEmailForm1";
    }

    @PostMapping("/sendmail")
    public String sendMail(Model model,emailForm form) {
        model.addAttribute("form", form);
        emailService.sendEmailTo1Person(form);
        return "redirect:/tbUser";
    }

    @GetMapping(value = "/setBuildingStatus/{status}/{id}")
    public String setBuildingStatus(@PathVariable String id,@PathVariable int status)  {
        Building building = buildingRepository.findById(id).get();
        if(status==0){
            building.setDanger(0);
        }else if(status==1){
            building.setDanger(1);
        }
        System.out.println(building);
        buildingRepository.save(building);
        return "redirect:/tbBuilding";
    }

    @GetMapping("/edit/{id}")
    public String editBuildingFrom(Model model,@PathVariable String id) {
        Building building = buildingRepository.findById(id).get();
        model.addAttribute("building",building);
        return "editBuilding";
    }

    @PostMapping("/edit/{id}")
    public String editBuilding(Model model,@PathVariable String id,Building building) {
        model.addAttribute("building", building);
        System.out.println(building);
        buildingRepository.save(building);
        return "redirect:/tbBuilding";
    }

    @PostMapping("/addBuilding")
    public String addBuilding(Model model,Building building) {
        model.addAttribute("addBuilding", building);
        boolean notCorrect = buildingRepository.findById(building.getBuilding_id()).isPresent() || building.getBuilding_id().length()==0;
        model.addAttribute("notCorrect",notCorrect);
        if(notCorrect){
            List<Building> buildings = buildingRepository.findAll();
            model.addAttribute("items", buildings);
            return "tbl_Building" ;
        }else {
            buildingRepository.save(building);
            return "redirect:/tbBuilding";
        }
    }


    @GetMapping("/setInfected/{ID}")
    public String setInfectedForm(Model model,@PathVariable String ID) {
        Check_in history = new Check_in();
        history.setStudent_id(ID);
        model.addAttribute("history",history);
        return "sendEmailForm2";
    }

    @PostMapping("/setInfected")
    public String setInfected(Model model,Check_in history) {
        model.addAttribute("history", history);
        System.out.println(history);

        if(history.getTime().length()==0){
            model.addAttribute("notSelectTime", true);
            return "sendEmailForm2";
        }else{
            List<String> studentIDs = check_inRepository.findByTime(history.getTime(),history.getStudent_id());
            if(studentIDs.size()==0){
                model.addAttribute("notSelectTime", true);
                return "sendEmailForm2";
            }
            StringBuilder  emailList = new StringBuilder() ;
            for(User user : userRepository.findAllById(studentIDs)) {
                emailList.append(user.getEmail()+",");
            }
            User user = userRepository.findById(history.getStudent_id()).get();
            user.setStatus("Infected");
            return "redirect:/sendmail/"+emailList.deleteCharAt(emailList.length()-1);
        }
    }
}