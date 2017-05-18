package com.ilibed.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u.id from #{#entityName} u where u.email = ?1")
    Integer findIdByEmail(String email);

    @Query("select u.firstName as firstName, u.lastName as lastName," +
            " p.path as path from User u, com.ilibed.photo.Photo p where p.id=u.mainPhotoId and u.id=?1")
    SimpleUser findById(Integer id);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
