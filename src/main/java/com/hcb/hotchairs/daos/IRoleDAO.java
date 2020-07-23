package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Role;
import com.hcb.hotchairs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Long> {

    @Query("FROM Role r WHERE r.id IN :idList")
    List<Role> findAllFromIdCollection(@Param("idList") Collection<Long> idList);
}
