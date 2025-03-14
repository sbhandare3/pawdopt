package com.sbhandare.pawdopt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class PetDTO {
    private long petid;

    private Long petfinderid;

    private String name;

    private String breed;

    private String typeCode;

    private String gender;

    private String age;

    private String color;

    private String coat;

    private String size;

    private String bio;

    private String image;

    private String isVaccinated;

    private String isSpayedNeutered;

    private String isGoodWithCats;

    private String isGoodWithChildren;

    private String isGoodWithDogs;

    private String isAdoptable;

    private String isDeclawed;

    private String isHouseTrained;

    private String isSpecialNeeds;

    private String tags;

    private OrganizationDTO organizationDTO;

    private boolean isCurrentUserFav;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public long getPetid() {
        return petid;
    }

    public void setPetid(long petid) {
        this.petid = petid;
    }

    public Long getPetfinderid() {
        return petfinderid;
    }

    public void setPetfinderid(Long petfinderid) {
        this.petfinderid = petfinderid;
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

    public String getIsVaccinated() {
        return isVaccinated;
    }

    public void setIsVaccinated(String isVaccinated) {
        this.isVaccinated = isVaccinated;
    }

    public String getIsSpayedNeutered() {
        return isSpayedNeutered;
    }

    public void setIsSpayedNeutered(String isSpayedNeutered) {
        this.isSpayedNeutered = isSpayedNeutered;
    }

    public String getIsGoodWithCats() {
        return isGoodWithCats;
    }

    public void setIsGoodWithCats(String isGoodWithCats) {
        this.isGoodWithCats = isGoodWithCats;
    }

    public String getIsGoodWithChildren() {
        return isGoodWithChildren;
    }

    public void setIsGoodWithChildren(String isGoodWithChildren) {
        this.isGoodWithChildren = isGoodWithChildren;
    }

    public String getIsGoodWithDogs() {
        return isGoodWithDogs;
    }

    public void setIsGoodWithDogs(String isGoodWithDogs) {
        this.isGoodWithDogs = isGoodWithDogs;
    }

    public String getIsAdoptable() {
        return isAdoptable;
    }

    public void setIsAdoptable(String isAdoptable) {
        this.isAdoptable = isAdoptable;
    }

    @JsonProperty("organization")
    public OrganizationDTO getOrganizationDTO() {
        return organizationDTO;
    }

    public void setOrganizationDTO(OrganizationDTO organizationDTO) {
        this.organizationDTO = organizationDTO;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String type) {
        this.typeCode = type;
    }

    public String getIsDeclawed() {
        return isDeclawed;
    }

    public void setIsDeclawed(String isDeclawed) {
        this.isDeclawed = isDeclawed;
    }

    public String getIsHouseTrained() {
        return isHouseTrained;
    }

    public void setIsHouseTrained(String isHouseTrained) {
        this.isHouseTrained = isHouseTrained;
    }

    public String getIsSpecialNeeds() {
        return isSpecialNeeds;
    }

    public void setIsSpecialNeeds(String isSpecialNeeds) {
        this.isSpecialNeeds = isSpecialNeeds;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isCurrentUserFav() {
        return isCurrentUserFav;
    }

    public void setCurrentUserFav(boolean currentUserFav) {
        isCurrentUserFav = currentUserFav;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}