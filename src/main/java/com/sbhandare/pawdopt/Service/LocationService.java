package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.DTO.AddressDTO;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface LocationService {
    ArrayList<BigDecimal> getLatLongFromAddress(AddressDTO addressDTO);
}
