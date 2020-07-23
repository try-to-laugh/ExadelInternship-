package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.dtos.PlaceFilterDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IPlaceFilterService {

    List<PlaceDTO> getFreePlaces(PlaceFilterDTO filter, Authentication authentication);
}
