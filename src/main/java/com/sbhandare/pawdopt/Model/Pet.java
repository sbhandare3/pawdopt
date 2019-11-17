package com.sbhandare.pawdopt.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="pet")
@EntityListeners(AuditingEntityListener.class)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int petid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "age", nullable = false)
    private String age;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "coat")
    private String coat;

    @Column(name = "size")
    private String size;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "vaccinated")
    private String isVaccinated;

    @Column(name = "spayedneutered")
    private String isSpayedNeutered;

    @Column(name = "goodwithcats")
    private String isGoodWithCats;

    @Column(name = "goodwithchildren")
    private String isGoodWithChildren;

    @Column(name = "goodwithdogs")
    private String isGoodWithDogs;

    @Column(name = "adoptable")
    private String isAdoptable;

    @Column(name = "declawed")
    private String isDeclawed;

    @Column(name = "housetrained")
    private String isHouseTrained;

    @Column(name = "specialneeds")
    private String isSpecialNeeds;

    @Column(name = "tags")
    private String tags;

    @ManyToOne
    @JoinColumn(name = "organizationid", nullable = false)
    private Organization organization;

    @ManyToMany(mappedBy = "likedPets")
    private Set<User> likedUsers;

    @ManyToOne
    @JoinColumn(name = "pettypeid", nullable = false)
    private PetType petType;

    public int getPetid() {
        return petid;
    }

    public void setPetid(int petid) {
        this.petid = petid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(String vaccinated) {
        isVaccinated = vaccinated;
    }

    public String isSpayedNeutered() {
        return isSpayedNeutered;
    }

    public void setSpayedNeutered(String spayedNeutered) {
        isSpayedNeutered = spayedNeutered;
    }

    public String isGoodWithCats() {
        return isGoodWithCats;
    }

    public void setGoodWithCats(String goodWithCats) {
        isGoodWithCats = goodWithCats;
    }

    public String isGoodWithChildren() {
        return isGoodWithChildren;
    }

    public void setGoodWithChildren(String goodWithChildren) {
        isGoodWithChildren = goodWithChildren;
    }

    public String isGoodWithDogs() {
        return isGoodWithDogs;
    }

    public void setGoodWithDogs(String goodWithDogs) {
        isGoodWithDogs = goodWithDogs;
    }

    public String isAdoptable() {
        return isAdoptable;
    }

    public void setAdoptable(String isAdoptable) {
        this.isAdoptable = isAdoptable;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String isDeclawed() {
        return isDeclawed;
    }

    public void setDeclawed(String isDeclawed) {
        this.isDeclawed = isDeclawed;
    }

    public String isHouseTrained() {
        return isHouseTrained;
    }

    public void setHouseTrained(String isHouseTrained) {
        this.isHouseTrained = isHouseTrained;
    }

    public String isSpecialNeeds() {
        return isSpecialNeeds;
    }

    public void setSpecialNeeds(String isSpecialNeeds) {
        this.isSpecialNeeds = isSpecialNeeds;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
