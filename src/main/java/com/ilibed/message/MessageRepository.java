package com.ilibed.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    @Query("select m from #{#entityName} m where m.senderId = ?1 or m.receiverId = ?1 order by m.sendDate")
    List<Message> findAllMessagesForUser(Integer userId);
    Iterable<Message> findAllBySenderIdOrReceiverId(Integer senderId, Integer receiverId);

}
