package com.sbhandare.pawdopt.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="userdetail")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int userid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(name = "user_like", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "petid"))
    private Set<Pet> likedPets;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secuid", referencedColumnName = "secuid")
    private SecurityUser securityUser;

    public Set<Pet> getLikedPets() {
        return likedPets;
    }

    public void setLikedPets(Set<Pet> likedPets) {
        this.likedPets = likedPets;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userId) {
        this.userid = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }
}
