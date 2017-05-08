package com.ilibed.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u.id from #{#entityName} u where u.email = ?1")
    Integer findIdByEmail(String email);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
