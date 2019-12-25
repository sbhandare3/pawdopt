package com.sbhandare.pawdopt.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pet_type")
@EntityListeners(AuditingEntityListener.class)
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long pettypeid;

    @Column(name = "type_code")
    private String petTypeCode;

    @Column(name = "type_desc")
    private String petTypeDesc;

    @OneToMany(mappedBy = "petType")
    private Set<Pet> petList;

    public long getPettypeid() {
        return pettypeid;
    }

    public void setPettypeid(long pettypeid) {
        this.pettypeid = pettypeid;
    }

    public String getPetTypeCode() {
        return petTypeCode;
    }

    public void setPetTypeCode(String petTypeCode) {
        this.petTypeCode = petTypeCode;
    }

    public String getPetTypeDesc() {
        return petTypeDesc;
    }

    public void setPetTypeDesc(String petTypeDesc) {
        this.petTypeDesc = petTypeDesc;
    }

    public Set<Pet> getPetList() {
        return petList;
    }

    public void setPetList(Set<Pet> petList) {
        this.petList = petList;
    }
}
