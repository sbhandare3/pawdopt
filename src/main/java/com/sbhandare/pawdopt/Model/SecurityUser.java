package com.sbhandare.pawdopt.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="security_user")
@EntityListeners(AuditingEntityListener.class)
public class SecurityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long secuid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "secuid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "securityUser")
    private User user;

    public long getSecuid() {
        return secuid;
    }

    public void setSecuid(long secuid) {
        this.secuid = secuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}