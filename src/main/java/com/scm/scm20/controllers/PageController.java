package com.scm.scm20.controllers;

import com.scm.scm20.forms.UserForm;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println("home page handler");
        model.addAttribute("name", "Substrings Technologies");
        model.addAttribute("Github_Repository", "https://github.com/neeraj552");
        return "home";
    }

    //about route
    @RequestMapping("/about")
    public String aboutpage(){
        System.out.println("about page loading");
        return "about";
    }
  // service route
  @RequestMapping("/services")
  public String servicpage(){
        System.out.println("services page loading");
        return "services";
  }
  //contact page
    @GetMapping("/contact")
    public String contactpage(){
        return new String("contact");
    }
    //registrationn page
    // register
    @GetMapping("/register")
    public String register(Model model){
        UserForm userform = new UserForm();
       // userform.setName("Neeraj");
        model.addAttribute("userForm", userform);
        return "register";
    }
    @GetMapping("/login")
    public String loginpage(){
        return new String("Login");
    }



    // register process
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userform, BindingResult rBindingResult, HttpSession session) {

        System.out.println("do-register page handler");
        //data fetch yoo men
        //userform
        System.out.println(userform);
        //validate form data
        if(rBindingResult.hasErrors()) {
            return "register";
        }
        //save to database
        //user Service
        //User user= User.builder()
        //        .name(userform.getName())
         //       .email(userform.getEmail())
         //       .password(userform.getPassword())
         //       .about(userform.getAbout())
         //       .phoneNumber(userform.getPhoneNumber())
         //       .provider(Providers.GOOGLE)
         //       .profilePic("https://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Transparent-Image.png")
        //.build();
        User user = new User();
        user.setName(userform.getName());
        user.setPassword(userform.getPassword());
        user.setEmail(userform.getEmail());
        user.setPhoneNumber(userform.getPhoneNumber());
        user.setAbout(userform.getAbout());
        user.setProfilePic("https://www.pngarts.com/files/10/Default-Profile-Picture-PNG-Transparent-Image.png");
      User savedUser=userService.saveUser(user);
      System.out.println("savedUser");
        //message="registration succeffull"
        //add message
      Message message=  Message.builder().content("Registered Successfully").type(MessageType.green).build();
        session.setAttribute("message",message);
        //redirect login page
        return "redirect:/register";
    }





}
