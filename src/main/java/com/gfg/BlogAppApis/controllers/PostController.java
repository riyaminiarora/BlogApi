package com.gfg.BlogAppApis.controllers;

import com.gfg.BlogAppApis.config.AppConstants;
import com.gfg.BlogAppApis.payloads.ApiResponse;
import com.gfg.BlogAppApis.payloads.PostDto;
import com.gfg.BlogAppApis.payloads.PostResponse;
import com.gfg.BlogAppApis.services.FileService;
import com.gfg.BlogAppApis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){
        PostDto createdPost=postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> posts=postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);

    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts=postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = "postId",required=false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = "asc",required=false) String sortDir)
    {
        PostResponse postResponse=postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto post=postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted",true);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedPost=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keywords){

        List<PostDto> result=postService.searchPosts(keywords);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {
        PostDto postDto=postService.getPostById(postId);
        String fileName=fileService.uploadImage(path,image);

        postDto.setImageName(fileName);
        PostDto updatedPost=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    @GetMapping(value="post/image/{imageName}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
        InputStream resource=fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
