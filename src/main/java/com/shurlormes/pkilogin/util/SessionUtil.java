package com.shurlormes.pkilogin.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SessionUtil {

    public static User currentUserDetails() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
