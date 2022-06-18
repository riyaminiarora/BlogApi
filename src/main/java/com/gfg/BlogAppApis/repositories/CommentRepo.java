package com.gfg.BlogAppApis.repositories;

import com.gfg.BlogAppApis.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
