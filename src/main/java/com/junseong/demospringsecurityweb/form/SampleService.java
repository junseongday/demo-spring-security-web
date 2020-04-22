package com.junseong.demospringsecurityweb.form;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {
    public void dashboard() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        final Object credentials = authentication.getCredentials();
        final boolean authenticated = authentication.isAuthenticated();
    }

}
