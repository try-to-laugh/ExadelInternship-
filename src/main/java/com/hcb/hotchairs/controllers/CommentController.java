package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.converters.CommentConverter;
import com.hcb.hotchairs.services.ICommentService;
import com.hcb.hotchairs.services.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping("/byUser/{id}")
    public ResponseEntity<Object> getByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok(commentService.getByUserId(id));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(commentService.getAll());
    }
}
