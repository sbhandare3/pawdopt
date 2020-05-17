package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.AddressDTO;
import com.sbhandare.pawdopt.Model.GeoPoint;
import com.sbhandare.pawdopt.Service.External.OpenStreetMapProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LocationServiceImpl implements LocationService {
    @Override
    public GeoPoint getLatLongFromAddress(AddressDTO addressDTO) {
        StringBuilder addressStrSb = new StringBuilder();
        /*
        if(!StringUtils.isEmpty(addressDTO.getStreet1()))
            addressStrSb.append(addressDTO.getStreet1()).append(", ");
        if(!StringUtils.isEmpty(addressDTO.getStreet2()))
            addressStrSb.append(addressDTO.getStreet2()).append(", ");
        */
        if(!StringUtils.isEmpty(addressDTO.getCity()))
            addressStrSb.append(addressDTO.getCity()).append(", ");
        if(!StringUtils.isEmpty(addressDTO.getState()))
            addressStrSb.append(addressDTO.getState()).append(", ");
        if(!StringUtils.isEmpty(addressDTO.getZipCode()))
            addressStrSb.append(addressDTO.getZipCode());

        //String addressStr = addressDTO.getStreet1()+", "+addressDTO.getStreet2()+", "+addressDTO.getCity()+", "+addressDTO.getState()+", "+addressDTO.getZipCode();
        try {
            return OpenStreetMapProcessor.getInstance().getCoordinates(addressStrSb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
