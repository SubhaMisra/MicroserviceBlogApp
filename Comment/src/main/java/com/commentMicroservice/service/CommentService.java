package com.commentMicroservice.service;

import com.commentMicroservice.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Comment comment);

    List<Comment> getAllCommentsByPostId(String postId);
}
