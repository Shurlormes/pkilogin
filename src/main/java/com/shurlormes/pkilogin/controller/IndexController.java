package com.shurlormes.pkilogin.controller;

import com.shurlormes.pkilogin.util.SessionUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;
import java.util.Collections;

@Controller
public class IndexController {


    @RequestMapping("")
    public String index(Model model) {
        User user = SessionUtil.currentUserDetails();
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if(certs != null) {
            X509Certificate gaX509Cert = certs[0];
            String dn = gaX509Cert.getSubjectDN().toString();
            System.out.println("个人证书信息:" + dn);
            String username = "";
            String[] dnArray = dn.split(",");
            for (String dnItem : dnArray) {
                String[] dnInfo = dnItem.split("=");
                String key = dnInfo[0];
                String value = dnInfo[1];
                if("cn".equalsIgnoreCase(key.trim())) {
                    username = value;
                    break;
                }
            }
            System.out.println("用户名:" + username);

            if(!StringUtils.isEmpty(username)) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                User userDetails = new User(username, "", Collections.EMPTY_LIST);
                securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "", Collections.EMPTY_LIST));
                return "redirect:/";
            }

        }
        return "login";
    }
}
