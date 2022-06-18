package com.gfg.BlogAppApis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer postId;
    @Column(length=100,nullable = false)
    private String title;
    @Column(length=10000,nullable = false)
    private String content;

    private String imageName;
    private Date addedDate;
    //Categry and User must always be provided to add a Post
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post",cascade=CascadeType.ALL) //makes foreign key in comment table
    private Set<Comment> comments=new HashSet<>();

}
