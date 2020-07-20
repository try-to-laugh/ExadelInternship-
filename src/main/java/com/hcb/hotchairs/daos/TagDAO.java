package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDAO extends JpaRepository<Tag, Long> {

}
