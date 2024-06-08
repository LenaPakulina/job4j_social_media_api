package ru.job4j.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;
import ru.job4j.model.Subscriber;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    @Query("""
            select user from User user
            where user.email = :email and user.password = :password
            """)
    Optional<User> findByEmailAndPassword(@Param("email") String email,
                                          @Param("password") String password);

    @Query("""
            select sub from Subscriber sub
            where sub.fromUser.id = ?1
            order by sub.fromUser.id asc
            """)
    List<Subscriber> findAllSubscribersById(int id);

    @Query("""
            select sub.fromUser from Subscriber sub
            where sub.toUser.id = ?1 and sub.fromUser.id in (
                select sub.toUser.id from Subscriber sub
                where sub.fromUser.id = ?1
            )
            """)
    List<User> findAllFriendsById(int id);

    @Modifying(clearAutomatically = true)
    @Query("""
            delete from User u where u.id = ?1
            """)
    int deleteUserById(int id);

    @Modifying(clearAutomatically = true)
    @Query("""
        update User u
        set u.name = :#{#user.name},
        u.email = :#{#user.email},
        u.timezone = :#{#user.timezone}
        where u.id=:#{#user.id}
        """)
    int update(@Param("user") User user);

    Optional<User> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
