package com.hcb.hotchairs.daos.restore;

import com.hcb.hotchairs.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IDBRestoreTagsDAO extends JpaRepository<Tag, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Tag")
    void clearTagTable();
}
