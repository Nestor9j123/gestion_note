package com.chance.coupchance.Controllers;

import javax.naming.AuthenticationException;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, @RequestParam boolean adminCheckBox) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = (Authentication) authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication) auth);
        
        // Vérifier si l'utilisateur a le rôle ADMIN
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            if (adminCheckBox) {
                return "redirect:/admin/dashboard"; // L'utilisateur est authentifié comme Admin
            } else {
                return "redirect:/user/dashboard"; // L'utilisateur a décoché l'option Admin
            }
        }
        return "redirect:/error";
    }
}
