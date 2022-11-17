package com.petsAdoption.oauth.util;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserJwt extends User {
    /*
    username – the username presented to the DaoAuthenticationProvider
    password – the password that should be presented to the DaoAuthenticationProvider
    enabled – set to true if the user is enabled
    accountNonExpired – set to true if the account has not expired
    credentialsNonExpired – set to true if the credentials have not expired
    accountNonLocked – set to true if the account is not locked
    authorities – the authorities that should be granted to the caller if they presented the correct username and password and the user is enabled. Not null.
    */
    private String id;    //用户ID
    private String name;  //用户名字
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean disabled;
    private List<GrantedAuthority> authorities;


    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserJwt(String username, String id, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UserJwt(String id,
                   String username,
                   String password,
                   boolean enabled,
                   boolean accountNonExpired,
                   boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities
                   ) {
        super(username,password,enabled,accountNonExpired,true,accountNonLocked,authorities);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
