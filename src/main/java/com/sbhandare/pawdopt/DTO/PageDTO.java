package com.sbhandare.pawdopt.DTO;

public class PageDTO {

    private Object listObj;

    private PaginationDTO pageDetails;

    public PageDTO(Object listObj, PaginationDTO pageDetails) {
        this.pageDetails = pageDetails;
        this.listObj = listObj;
    }

    public PaginationDTO getPageDetails() {
        return pageDetails;
    }

    public void setPageDetails(PaginationDTO pageDetails) {
        this.pageDetails = pageDetails;
    }

    public Object getListObj() {
        return listObj;
    }

    public void setListObj(Object listObj) {
        this.listObj = listObj;
    }
}
