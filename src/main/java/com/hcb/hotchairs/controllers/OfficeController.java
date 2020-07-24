package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.CityDTO;
import com.hcb.hotchairs.dtos.CountryDTO;
import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.services.ICityService;
import com.hcb.hotchairs.services.ICountryService;
import com.hcb.hotchairs.services.IFloorService;
import com.hcb.hotchairs.services.IOfficeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private final IOfficeService officeService;
    private final ICityService cityService;
    private final ICountryService countryService;
    private final IFloorService floorService;

    @Autowired
    public OfficeController(IOfficeService officeService,
                            ICityService cityService,
                            ICountryService countryService,
                            IFloorService floorService) {
        this.officeService = officeService;
        this.cityService = cityService;
        this.countryService = countryService;
        this.floorService = floorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(officeService.getById(id));
    }

    @GetMapping("/byCity/{id}")
    public ResponseEntity<Object> getByCityId(@PathVariable("id") Long id){
        return ResponseEntity.ok(officeService.getAllByCityId((id)));
    }

    @PostMapping("")
    public ResponseEntity<OfficeDTO> saveOffice(@RequestBody OfficeDTO newOffice) {
        OfficeDTO savedOffice = officeService.saveOffice(newOffice);

        return Objects.isNull(savedOffice.getId()) ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(savedOffice);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(officeService.getAll());
    }

    /*
        This method is Deprecated and will be removed after front transition to pageable version.
     */
    @Deprecated
    @GetMapping("/extended")
    public ResponseEntity<?> getExtendedOfficeInfo() {
        List<OfficeDTO> offices = officeService.getAll();

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class ExtendedOfficeInfo {
            List<FloorDTO> floors;
            private OfficeDTO office;
            private CityDTO city;
            private CountryDTO country;
        }

        List<ExtendedOfficeInfo> extendedOfficeInfos = new ArrayList<>();
        for (OfficeDTO office: offices) {
            List<FloorDTO> floors = floorService.getAllByOfficeId(office.getId());
            CityDTO city = cityService.getById(office.getCityId());
            CountryDTO country = countryService.getById(city.getCountryId());

            extendedOfficeInfos.add(new ExtendedOfficeInfo(floors, office, city, country));
        }

        return ResponseEntity.ok(extendedOfficeInfos);
    }

    @GetMapping("/extended/paging")
    public ResponseEntity<?> getExtendedPagingAndSortingOfficeInfo(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                                   @RequestParam(name = "pageSize") Integer pageSize,
                                                                   @RequestParam(name = "sortMethod") String sortMethod) {
        List<OfficeDTO> offices = officeService.getPagedAndSorted(pageNumber, pageSize, sortMethod);

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class ExtendedOfficeInfo {
            List<FloorDTO> floors;
            private OfficeDTO office;
            private CityDTO city;
            private CountryDTO country;
        }

        List<ExtendedOfficeInfo> extendedOfficeInfos = new ArrayList<>();
        for (OfficeDTO office: offices) {
            List<FloorDTO> floors = floorService.getAllByOfficeId(office.getId());
            CityDTO city = cityService.getById(office.getCityId());
            CountryDTO country = countryService.getById(city.getCountryId());

            extendedOfficeInfos.add(new ExtendedOfficeInfo(floors, office, city, country));
        }

        return ResponseEntity.ok(extendedOfficeInfos);
    }
}