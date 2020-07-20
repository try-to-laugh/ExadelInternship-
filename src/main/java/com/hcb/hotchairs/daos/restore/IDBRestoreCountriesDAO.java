package com.hcb.hotchairs.daos.restore;

import com.hcb.hotchairs.entities.Country;
import com.hcb.hotchairs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IDBRestoreCountriesDAO extends JpaRepository<Country, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Country")
    void clearCountryTable();
}
