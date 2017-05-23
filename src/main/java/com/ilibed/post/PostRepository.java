package com.ilibed.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query("select p.text as text, p.creationDate as creationDate, ph.path as photoPath, " +
            " u.id as id, u.ownerId as ownerId from #{#entityName} p, UserPost u, Photo ph where u.userId = ?1 " +
            "and p.id=u.postId and p.photoId=ph.id order by p.creationDate")
    List<PostProjection> findAllPostsForUser(Integer userId);

    @Query("select p.text as text, p.creationDate as creationDate, ph.path as photoPath, " +
            " u.id as id, u.ownerId as ownerId from #{#entityName} p, UserPost u, com.ilibed.photo.Photo ph where u.id = ?1 " +
            "and p.id=u.postId and p.photoId=ph.id")
    PostProjection findPostProjectionById(Integer postId);
}
