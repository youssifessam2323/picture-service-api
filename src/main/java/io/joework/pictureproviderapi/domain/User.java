package io.joework.pictureproviderapi.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.Collection;

import lombok.Data;


@Data
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username ;
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Image> userImages;
}
