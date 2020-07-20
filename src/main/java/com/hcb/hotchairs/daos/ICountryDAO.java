package com.hcb.hotchairs.daos;


import com.hcb.hotchairs.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryDAO extends JpaRepository<Country, Long> {
    List<Country> findAll();
}
