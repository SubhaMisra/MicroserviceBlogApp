package com.postMicroservice.service.serviceImpl;

import com.postMicroservice.config.RestTemplateConfig;
import com.postMicroservice.entity.Post;
import com.postMicroservice.payload.PostDto;
import com.postMicroservice.repository.PostRepository;
import com.postMicroservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplateConfig restTemplateConfig;

    public Post savePost(Post post)
    {
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Post findPostById(String postId) {

        Post post = postRepository.findById(postId).get();
        return post;
    }

    public PostDto getPostWithComments(String postId) {

        Post post = postRepository.findById(postId).get();

        ArrayList comments = restTemplateConfig.getRestTemplate().getForObject("http://COMMENT-SERVICE:8082/api/comments/" + postId, ArrayList.class);

        PostDto postDto=new PostDto();

        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        postDto.setComments(comments);

        return postDto;
    }
}
