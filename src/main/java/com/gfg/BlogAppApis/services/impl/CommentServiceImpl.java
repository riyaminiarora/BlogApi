package com.gfg.BlogAppApis.services.impl;

import com.gfg.BlogAppApis.entities.Comment;
import com.gfg.BlogAppApis.entities.Post;
import com.gfg.BlogAppApis.exceptions.ResourceNotFoundException;
import com.gfg.BlogAppApis.payloads.CommentDto;
import com.gfg.BlogAppApis.repositories.CommentRepo;
import com.gfg.BlogAppApis.repositories.PostRepo;
import com.gfg.BlogAppApis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," post id",postId));
        Comment comment=modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment=commentRepo.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment"," comment id",commentId));
        commentRepo.delete(comment);
    }
}
