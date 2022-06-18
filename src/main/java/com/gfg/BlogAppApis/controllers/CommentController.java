package com.gfg.BlogAppApis.controllers;

import com.gfg.BlogAppApis.payloads.ApiResponse;
import com.gfg.BlogAppApis.payloads.CommentDto;
import com.gfg.BlogAppApis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createdComment=commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true), HttpStatus.OK);
    }


}
