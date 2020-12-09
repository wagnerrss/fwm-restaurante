package com.fwm.restaurante.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * Spring multi-tenancy implementation.
 * <p>
 * Tenant test data repository.
 *
 * @author Ranjith Manickam
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "SELECT * FROM ECOMER_LOGIN  WHERE EMALOG = :EMALOG AND PASLOG = :PASLOG" , nativeQuery = true)
    public Map findLogin(@Param("EMALOG") String EMALOG, @Param("PASLOG") String PASLOG);


}
