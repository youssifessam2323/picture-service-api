package io.joework.pictureproviderapi.domain;

import static io.joework.pictureproviderapi.domain.Permission.PICTURE_ACCEPT;
import static io.joework.pictureproviderapi.domain.Permission.PICTURE_READ;
import static io.joework.pictureproviderapi.domain.Permission.PICTURE_WRITE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    ADMIN(Collections.unmodifiableList(List.of(PICTURE_READ, PICTURE_ACCEPT, PICTURE_WRITE))),
    USER(Collections.unmodifiableList(List.of(PICTURE_READ)));


    private Collection<Permission> permissions;

    private Role(Collection<Permission> permissions){
        this.permissions = permissions;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }


    public Collection<SimpleGrantedAuthority> getAuthorities(){
        String roleName = this.name();

        var authorities = this.permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority(roleName));
        
        return authorities;
    }
}
