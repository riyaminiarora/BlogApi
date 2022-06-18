package com.gfg.BlogAppApis.repositories;

import com.gfg.BlogAppApis.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
