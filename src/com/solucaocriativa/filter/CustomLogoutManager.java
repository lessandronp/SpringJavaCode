package com.solucaocriativa.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *
 * @author Lessandro
 */
public class CustomLogoutManager implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest hsr, HttpServletResponse hsr1, Authentication a) {
        System.out.println("Logout");
    }
}
