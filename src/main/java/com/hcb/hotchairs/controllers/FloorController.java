package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.FloorConverter;
import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.services.IFloorService;
import com.hcb.hotchairs.services.impl.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/floors")
public class FloorController {

    private final IFloorService floorService;

    @Autowired
    FloorController(IFloorService floorService){
        this.floorService = floorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(floorService.getById(id));
    }

    @GetMapping("/byOffice/{id}")
    public ResponseEntity<Object> getByOfficeId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(floorService.getAllByOfficeId(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(floorService.getAll());
    }

    @PostMapping("/batch")
    @Deprecated
    public ResponseEntity<Object> saveBatchFloors(@RequestBody List<FloorDTO> floors) {
        List<FloorDTO> savedFloors = floorService.saveBatch(floors);

        return Objects.isNull(savedFloors) ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(savedFloors);
    }

    @PostMapping("")
    public ResponseEntity<FloorDTO> saveFloor(@RequestBody FloorDTO floor) {
        FloorDTO savedFloor = floorService.save(floor);

        return Objects.isNull(savedFloor.getId()) ? ResponseEntity.unprocessableEntity().build()
                : ResponseEntity.ok(savedFloor);
    }
}