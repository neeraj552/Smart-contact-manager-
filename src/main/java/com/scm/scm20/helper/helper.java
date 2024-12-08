package com.scm.scm20.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        //sigin self
        if (authentication instanceof OAuth2AuthenticationToken) {
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User= (OAuth2User)authentication.getPrincipal();
            String username="";
            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting Email from Google");
               username=oauth2User.getAttribute("email").toString();
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.println("Getting Email from GitHub");
                username=oauth2User.getAttribute("email") !=null ?
                        oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return username;
        } else {
            System.out.println("Getting Email from user");
            return authentication.getName();
        }
    }
}
