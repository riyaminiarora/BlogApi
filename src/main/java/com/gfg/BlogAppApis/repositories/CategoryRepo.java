package com.gfg.BlogAppApis.repositories;

import com.gfg.BlogAppApis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {

}
