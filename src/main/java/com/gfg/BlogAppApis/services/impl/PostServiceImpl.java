package com.gfg.BlogAppApis.services.impl;

import com.gfg.BlogAppApis.entities.Category;
import com.gfg.BlogAppApis.entities.Post;
import com.gfg.BlogAppApis.entities.User;
import com.gfg.BlogAppApis.exceptions.ResourceNotFoundException;
import com.gfg.BlogAppApis.payloads.PostDto;
import com.gfg.BlogAppApis.payloads.PostResponse;
import com.gfg.BlogAppApis.repositories.CategoryRepo;
import com.gfg.BlogAppApis.repositories.PostRepo;
import com.gfg.BlogAppApis.repositories.UserRepo;
import com.gfg.BlogAppApis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," user id",userId));
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," category id",categoryId));
        Post post=modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=postRepo.save(post);
        return modelMapper.map(newPost,PostDto.class);
    }
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost=postRepo.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }
    @Override
    public void deletePost(Integer postId) {
        Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        postRepo.delete(post);

    }
    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {//pageNumber starts from 0
//        int pageSize=5;
//        int pageNumber=1;
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost=postRepo.findAll(p);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map(post->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setIslastPage(pagePost.isLast());
        postResponse.setPageSize(pagePost.getSize());
        return postResponse;
    }
    @Override
    public PostDto getPostById(Integer postId) {
        Post post= postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post"," post id ",postId));
        return modelMapper.map(post,PostDto.class);
    }
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category"," category id",categoryId));
        List<Post> posts= postRepo.findByCategory(category);
        List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," user id",userId));
        List<Post> posts= postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos= posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
