package com.scm.scm20.config;

import com.scm.scm20.entities.Providers;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger = Logger.getLogger(String.valueOf(OAuthAuthenticationSuccessHandler.class));
    @Autowired
    private UserRepo userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
         logger.info("Authentication success");
         //identifying user

        var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);
        var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key,value)->{
            logger.info(key+":"+value);
        });
        User user=new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleLists(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {


            //google
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setPassword("dummy");
            user.setAbout("This acc is created by google account");
        }

       else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            //github
            String email=oauthUser.getAttribute("email") !=null ?
                    oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString()+"@gmail.com";
            String picture=oauthUser.getAttribute("avatar_url").toString();
            String name=oauthUser.getAttribute("login").toString();
            String providerUserId=oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setPassword("dummy");
            user.setAbout("This account is created by github");
        }

       else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {

        }else{
           logger.info(authorizedClientRegistrationId);
        }
       // DefaultOAuth2User user=(DefaultOAuth2User)authentication;

      /*   logger.info(user.getName());
         user.getAttributes().forEach((key,value)->{
            logger.info("{} => {}");
        });
         logger.info(user.getAttributes().toString());*/
       /* String email=user.getAttribute("email").toString();
        String name=user.getAttribute("name").toString();
        String picture=user.getAttribute("picture").toString();
        //save in data base
        User user1=new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(user1.getName());
        user1.setRoleLists(List.of("ROLE_USER"));
        user1.setAbout("Created by Google");
        User user2=userRepo.findByEmail(email).orElse(null);
        if(user2 ==null) {
            userRepo.save(user1);
            logger.info("User saved" +email);
        }
        */
        User user2=userRepo.findByEmail(user.getEmail()).orElse(null);
        if(user2 ==null) {
            userRepo.save(user);
            logger.info("user saved" + user.getEmail());
        }

         new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
