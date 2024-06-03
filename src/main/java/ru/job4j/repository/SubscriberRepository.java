package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Subscriber;
import ru.job4j.model.User;

public interface SubscriberRepository extends ListCrudRepository<Subscriber, Integer> {
    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE FROM Subscriber sub
            WHERE sub.fromUser.id = :fromUserId AND sub.toUser.id = :toUserId
            """)
    void deleteSubscription(@Param("fromUserId") Integer fromUserId,
                            @Param("toUserId") Integer toUserId);
}
