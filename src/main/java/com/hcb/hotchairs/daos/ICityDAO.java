package com.hcb.hotchairs.daos;

import com.hcb.hotchairs.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityDAO extends JpaRepository<City, Long> {
    List<City> findAllByCountryId(Long id);
}
