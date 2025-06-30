package com.postMicroservice.service;

import com.postMicroservice.entity.Post;
import com.postMicroservice.payload.PostDto;

public interface PostService {
    Post savePost(Post post);

    Post findPostById(String postId);

    PostDto getPostWithComments(String postId);
}
