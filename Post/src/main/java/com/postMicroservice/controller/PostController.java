package com.postMicroservice.controller;

import com.postMicroservice.entity.Post;
import com.postMicroservice.payload.PostDto;
import com.postMicroservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> savePost(@RequestBody Post post)
    {
        Post newPost = postService.savePost(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    // http://localhost:8081/api/posts/a62f302e-3eb5-4e99-86e7-66c75eafefcc
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable("id") String postId)
    {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    // http://localhost:8081/api/posts/a62f302e-3eb5-4e99-86e7-66c75eafefcc/comments
    @GetMapping("/{postId}/comments")
    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId)
    {
        PostDto postDto= postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
}
