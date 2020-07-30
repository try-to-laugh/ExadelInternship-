package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.services.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/floors")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFloor(@PathVariable("id") Long id){

        if(!floorService.deleteById(id)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/map/{id}")
    public ResponseEntity<byte[]> getFloorMap(@PathVariable Long id) {
        byte[] svg = floorService.getFloorMap(id);

        if (Objects.isNull(svg)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/svg+xml")).body(svg);
        }
    }

    @PostMapping("/map/{id}")
    public ResponseEntity<Object> setFloorMap(@RequestBody byte[] svg, @PathVariable Long id) {
        if (floorService.setFloorMap(svg, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("map/{id}")
    public ResponseEntity<Object> deleteFloorMap(@PathVariable Long id) {
        if (floorService.deleteFloorMap(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}