package com.dalgo.SpringBoot.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String getHealthStatus(){
        return "All is OK";
    }

    // this will return SessionID
    @GetMapping("id")
    public String getJSessionId(HttpServletRequest httpServletRequest){
        return "Session ID: "+ httpServletRequest.getSession().getId();
    }
//    // eta string hishabe return kore
//    @GetMapping("csrf")
//    public String getCsrfToken(HttpServletRequest httpServletRequest){
//        return "CSRF TOKEN: " + httpServletRequest.getAttribute("_csrf");
//    }

    @GetMapping("csrf")
    public CsrfToken getCsrfToken(HttpServletRequest httpServletRequest){
        return (CsrfToken)httpServletRequest.getAttribute("_csrf");
    }
}
