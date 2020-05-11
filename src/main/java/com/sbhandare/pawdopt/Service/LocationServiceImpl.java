package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.AddressDTO;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LocationServiceImpl implements LocationService {
    @Override
    public ArrayList<BigDecimal> getLatLongFromAddress(AddressDTO addressDTO) {
        /*
        String addressStr = addressDTO.getStreet1()+", "+addressDTO.getStreet2()+", "+addressDTO.getCity()+", "+addressDTO.getState()+", "+addressDTO.getZipCode();
        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<AddressDTO> addressList;
        GeoPoint p1 = null;

        try {
            addressList = coder.getFromLocationName(addressStr,5);
            if (addressList==null) {
                return null;
            }
            AddressDTO location = addressList.get(0);

            p1 = new GeoPoint(location.getLatitude(), location.getLongitude());

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        return null;
    }
}
