package com.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

	
	// http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/
	
	// post >>> http://localhost:8080/RestFulOauth2WebService/oauth/token?grant_type=password&username=anacondong&password=password
	// username : my-trusted-client 
	// password : secret
	
	// get >>> http://localhost:8080/RestFulOauth2WebService/private?access_token=618ba847-91d9-486b-aa50-8abfb0e881d7
	
	
	@RequestMapping("/")
    public String indexHandle(Model model) {
        model.addAttribute("msg", "index anacondong here !!");
        return "index";
    }
	@RequestMapping("/home")
    public String homeHandle(Model model) {
        model.addAttribute("msg", "HOME anacondong here !!");
        return "home";
    }
	
	@RequestMapping("/private")
    public String privateHandle(Model model) {
        model.addAttribute("msg", "private!!");
        return "home";
    }
	
	@RequestMapping("/error")
    public String errorHandle(Model model) {
        model.addAttribute("msg", "Error !!!!!");
        return "error";
    }

}
