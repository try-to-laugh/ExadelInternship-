package com.hcb.hotchairs.controllers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcb.hotchairs.dtos.CityDTO;
import com.hcb.hotchairs.dtos.CountryDTO;
import com.hcb.hotchairs.services.ICountryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final ICountryService countryService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public CountryController(ICountryService countryService, ResourceLoader resourceLoader) {
        this.countryService = countryService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping(value = "/images/active/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getActiveCountryImage(@PathVariable String name) {
        Resource image =
                resourceLoader.getResource(String.format("classpath:static/countries/active/%s.png", name.toLowerCase()));

        if (image.exists()) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/images/passive/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getPassiveCountryImage(@PathVariable String name) {
        Resource image =
                resourceLoader.getResource(String.format("classpath:static/countries/passive/%s.png", name.toLowerCase()));

        if (image.exists()) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<CountryDTO>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<List<CityDTO>> getCountryCities(@PathVariable("id") Long id) {
        return ResponseEntity.ok(countryService.getCountryCities(id));
    }

    @GetMapping("/extended/{id}")
    public ResponseEntity<Object> getExtendedCountryInfo(@PathVariable("id") Long id) {
        CountryDTO countryDTO = countryService.getById(id);
        List<CityDTO> cityDTOList = countryService.getCountryCities(id);

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class ExtendedCountryInfo {
            @JsonUnwrapped
            private CountryDTO country;
            private List<CityDTO> cities;
        }

        return ResponseEntity.ok(new ExtendedCountryInfo(countryDTO, cityDTOList));
    }

    @GetMapping("/extended")
    public ResponseEntity<?> getAllExtendedCountryInfo() {
        List<CountryDTO> countryDTOList = countryService.getAll();

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class ExtendedCountryInfo {
            @JsonUnwrapped
            private CountryDTO country;
            private List<CityDTO> cities;
        }

        List<ExtendedCountryInfo> extendedCountryInfos = new ArrayList<>();
        for (CountryDTO country: countryDTOList) {
            extendedCountryInfos.add(new ExtendedCountryInfo(country, countryService.getCountryCities(country.getId())));
        }

        return ResponseEntity.ok(extendedCountryInfos);
    }
}
