package com.gfg.BlogAppApis.payloads;

import com.gfg.BlogAppApis.entities.Category;
import com.gfg.BlogAppApis.entities.Comment;
import com.gfg.BlogAppApis.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments=new HashSet<>();
}
