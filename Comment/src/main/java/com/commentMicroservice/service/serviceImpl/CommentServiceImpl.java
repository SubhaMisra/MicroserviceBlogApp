package com.commentMicroservice.service.serviceImpl;

import com.commentMicroservice.config.RestTemplateConfig;
import com.commentMicroservice.entity.Comment;
import com.commentMicroservice.payload.Post;
import com.commentMicroservice.repository.CommentRepository;
import com.commentMicroservice.service.CommentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private static final String SERVICE_NAME = "COMMENT-SERVICE";

    private static final String POST_SERVICE_URL = "http://POST-SERVICE/api/posts/";

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @CircuitBreaker(name = SERVICE_NAME,fallbackMethod = "getDefault")
    public Comment saveComment(Comment comment)
    {
        Post post = restTemplateConfig.getRestTemplate().getForObject( POST_SERVICE_URL+ comment.getPostId(), Post.class);

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

    public Comment getDefault(Comment comment, Exception e) {
        System.out.println("***** fallback method called... *******");
        return new Comment();
    }
}
