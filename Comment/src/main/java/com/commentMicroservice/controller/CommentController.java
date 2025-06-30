package com.commentMicroservice.controller;


import com.commentMicroservice.entity.Comment;
import com.commentMicroservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8082/api/comments
    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment)
    {
        Comment saveComment = commentService.saveComment(comment);
        return new ResponseEntity<>(saveComment, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentsByPostId(@PathVariable String postId)
    {
        List<Comment> comments=commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}
