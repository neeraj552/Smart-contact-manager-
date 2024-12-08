package com.scm.scm20.helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {
    public static void removeMessage(){
        System.out.println("Removing message");
        try{
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest()
                    .getSession();
            session.removeAttribute("message");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
