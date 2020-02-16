package com.sbhandare.pawdopt.DTO;

public class PetTypeDTO {

    private long pettypeid;

    private String petTypeCode;

    private String petTypeDesc;

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
}
