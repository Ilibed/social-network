package com.ilibed.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFriendRepository extends CrudRepository<UserFriend, Integer> {
    @Query("select uf.friendId from UserFriend uf where uf.userId=?1")
    List<Integer> findFriends(Integer id);

    UserFriend findByUserIdAndFriendId(Integer userId, Integer friendId);
}
