package com.sbhandare.pawdopt.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name="address")
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int addressid;

    @Column(name = "street1", nullable = false)
    private String street1;

    @Column(name = "street2")
    private String street2;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable =  false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zipcode", nullable = false)
    private String zipCode;

    @OneToOne(mappedBy = "address")
    private Organization organization;

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
