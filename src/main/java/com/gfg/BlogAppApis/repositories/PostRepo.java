package com.gfg.BlogAppApis.repositories;

import com.gfg.BlogAppApis.entities.Category;
import com.gfg.BlogAppApis.entities.Post;
import com.gfg.BlogAppApis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title); //search based on title

}
