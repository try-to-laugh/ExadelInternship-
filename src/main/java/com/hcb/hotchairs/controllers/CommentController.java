package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.CommentDTO;
import com.hcb.hotchairs.services.ICommentService;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final ICommentService commentService;
    private final IUserService userService;

    @Autowired
    public CommentController(ICommentService commentService, IUserService userService){
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<CommentDTO> getCommentByReservationId(@PathVariable("id") Long reservationId) {
        return ResponseEntity.ok(commentService.getByReservationId(reservationId));
    }

    @PostMapping("/reservation/{id}")
    public ResponseEntity<CommentDTO> saveCommentByReservationId(@PathVariable("id") Long reservationId,
                                                  @RequestBody String text,
                                                  Authentication authentication) {
        if (Objects.isNull(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(commentService.saveCommentByReservationId(
                reservationId,
                userService.getByEmail(authentication.getName()).getId(),
                text));
    }

    @GetMapping("/byPlace/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPlaceId(@PathVariable("id") Long placeId) {
        return ResponseEntity.ok(commentService.getAllByPlaceId(placeId));
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<List<CommentDTO>> getAllByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getAllByUserId(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(commentService.getAll());
    }
}
