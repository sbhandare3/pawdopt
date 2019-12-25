package com.sbhandare.pawdopt.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="role")
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long roleid;

    @Column(name = "rolename", nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<SecurityUser> securityUsers;

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
    }
}