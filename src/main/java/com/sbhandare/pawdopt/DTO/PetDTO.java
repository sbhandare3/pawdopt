package com.sbhandare.pawdopt.DTO;

public class PetDTO {
    private int petid;

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

    private OrganizationDTO organizationDTO;

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
}