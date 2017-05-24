package com.ilibed.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u.id from #{#entityName} u where u.email = ?1")
    Integer findIdByEmail(String email);

    @Query("select u.id as id, u.firstName as firstName, u.lastName as lastName," +
            " p.path as path from User u, com.ilibed.photo.Photo p where p.id=u.mainPhotoId and u.id=?1")
    SimpleUser findSimpleById(Integer id);

    @Query("select u.id as id, u.firstName as firstName, u.lastName as lastName," +
            " p.path as path, u.email as email, u.city as city, u.country as country" +
            " from User u, com.ilibed.photo.Photo p where p.id=u.mainPhotoId and u.id=?1")
    PresentationUser findPresentationUser(Integer id);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.firstName=?1, u.lastName=?2, u.country=?3, u.city=?4, u.mainPhotoId=?5" +
            " where u.id=?6")
    void update(String firstName, String lastName, String country, String city, Integer mainPhotoId,
                Integer id);
}
