package com.app.evento.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ServletUtil {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
