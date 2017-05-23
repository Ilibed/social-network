package com.ilibed.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostRepository extends CrudRepository<UserPost, Integer> {

}
