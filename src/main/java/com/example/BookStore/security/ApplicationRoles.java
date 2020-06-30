package com.example.BookStore.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.BookStore.security.ApplicationUserPermission.USER_READ;
import static com.example.BookStore.security.ApplicationUserPermission.USER_WRITE;

public enum ApplicationRoles {

    USER_LOGIN(Sets.newHashSet(USER_READ,USER_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationRoles(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
