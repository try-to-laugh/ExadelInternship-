package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagDAO extends JpaRepository<Tag, Long> {

    @Query("FROM Tag t WHERE t.id IN :idList")
    List<Tag> findAllFromIdCollection(@Param("idList") Collection<Long> idList);
}
