package com.usa.ciclo4.reto1.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.usa.ciclo4.reto1.model.User;

public class CustomUserDetails implements UserDetails {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1056271089079272597L;
	private User user;
    
    /**
     * @param user
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getName() {
    	return user.getName();
    }

}