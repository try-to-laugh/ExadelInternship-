package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetailDAO extends JpaRepository<Detail, Long>{
}