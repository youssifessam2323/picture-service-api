package io.joework.pictureproviderapi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;


@Data
@Entity
@Table(indexes = {@Index(name ="username_idx", columnList = "username", unique = true)})
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String username ;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String password;
    private Role role;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Collection<Image> userImages = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    public void addImage(Image image){
        userImages.add(image);
        image.setUser(this);
    }

    public void removeImage(Image image){
        userImages.remove(image);
        image.setUser(null);
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
        // TODO Auto-generated method stub
        return true;
    }


    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
