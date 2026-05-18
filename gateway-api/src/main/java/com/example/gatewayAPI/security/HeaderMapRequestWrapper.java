package com.example.gatewayAPI.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;


import java.util.*;


public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public  HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void addHeader(String headerName, String headerValue) {
        this.customHeaders.put(headerName, headerValue);
    }

    public void removeHeader(String headerName) {
        this.customHeaders.remove(headerName);
    }

    @Override
    public String getHeader(String name) {
        if (customHeaders.containsKey(name)) {
            return customHeaders.get(name);
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            String value = customHeaders.get(name);
            if (value == null) {
                return Collections.emptyEnumeration();
            }
            return Collections.enumeration(Collections.singletonList(value));
        }
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>();
        Enumeration<String> originalNames = super.getHeaderNames();
        while (originalNames.hasMoreElements()) {
            names.add(originalNames.nextElement());
        }
        names.addAll(customHeaders.keySet());
        names.removeIf(name -> customHeaders.get(name) == null);
        return Collections.enumeration(names);
    }
}
