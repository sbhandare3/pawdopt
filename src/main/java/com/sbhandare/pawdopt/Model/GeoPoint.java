package com.sbhandare.pawdopt.Model;

import java.math.BigDecimal;

public class GeoPoint {
    BigDecimal lattitude;
    BigDecimal longitude;

    public GeoPoint(BigDecimal lat, BigDecimal lon){
        this.lattitude = lat;
        this.longitude = lon;
    }

    public BigDecimal getLattitude() {
        return lattitude;
    }

    public void setLattitude(BigDecimal lattitude) {
        this.lattitude = lattitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

}
