package com.fwm.restaurante.repository;

import com.fwm.restaurante.domain.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LoginRepository extends JpaRepository<SystemUser, Integer> {

    @Query(value = "SELECT * FROM SYSTEM_USER WHERE USERNAME = :USERNAME AND PASSWORD = :PASSWORD", nativeQuery = true)
    public SystemUser findByUsernamePassword(@Param("USERNAME") String USERNAME,
                                             @Param("PASSWORD") String PASSWORD);

}
