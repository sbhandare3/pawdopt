package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.AddressDTO;
import com.sbhandare.pawdopt.Model.GeoPoint;

public interface LocationService {
    GeoPoint getLatLongFromAddress(AddressDTO addressDTO);
}
