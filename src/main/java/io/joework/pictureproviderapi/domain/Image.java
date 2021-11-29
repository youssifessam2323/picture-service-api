package io.joework.pictureproviderapi.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String description;
    private Category category;
    private  byte[] attachement;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;    

        if(!(obj instanceof Image)) return false;

        return id != null && id.equals(((Image)obj).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
