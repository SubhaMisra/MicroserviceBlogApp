package com.commentMicroservice.service.serviceImpl;

import com.commentMicroservice.config.RestTemplateConfig;
import com.commentMicroservice.entity.Comment;
import com.commentMicroservice.payload.Post;
import com.commentMicroservice.repository.CommentRepository;
import com.commentMicroservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    public Comment saveComment(Comment comment)
    {
        Post post = restTemplateConfig.getRestTemplate().getForObject("http://localhost:8081/api/posts/" + comment.getPostId(), Post.class);

        if(post!=null)
        {
            String commentId = UUID.randomUUID().toString();
            comment.setCommentId(commentId);
            Comment savedComment = commentRepository.save(comment);
            return savedComment;
        }else {
            return null;
        }
    }

    public List<Comment> getAllCommentsByPostId(String postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments;
    }
}
