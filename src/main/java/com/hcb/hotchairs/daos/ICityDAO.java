package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityDAO extends JpaRepository<City, Long> {

    @Query("FROM City WHERE Country.id = ?1")
    List<City> findAllByCountryId(Long countryId);
}
