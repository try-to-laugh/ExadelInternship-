package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.services.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    private final ResourceLoader resourceLoader;

    @Autowired
    FloorController(IFloorService floorService, ResourceLoader resourceLoader){
        this.floorService = floorService;
        this.resourceLoader = resourceLoader;
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
    
    @GetMapping("/checkMap/{id}")
    public ResponseEntity<Boolean> checkIfMapExists(@PathVariable Long id) {
        if (Objects.isNull(floorService.getFloorMap(id))) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
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

    @DeleteMapping("/map/{id}")
    public ResponseEntity<Object> deleteFloorMap(@PathVariable Long id) {
        if (floorService.deleteFloorMap(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping(value = "/plan/default", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getDefaultPlan() {
        Resource image =
                resourceLoader.getResource("classpath:static/floors/plans/default.png");

        if (image.exists()) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/plan/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getFloorPlan(@PathVariable Long id) {
        byte[] png = floorService.getFloorPlan(id);

        if (Objects.isNull(png)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(png);
        }
    }

    @PostMapping("/plan/{id}")
    public ResponseEntity<Object> setFloorPlan(@RequestBody byte[] png, @PathVariable Long id) {
        if (floorService.setFloorPlan(png, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/plan/{id}")
    public ResponseEntity<Object> deleteFloorPlan(@PathVariable Long id) {
        if (floorService.deleteFloorPlan(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

}