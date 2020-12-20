package com.deptManager.deptManager.repositories;

import com.deptManager.deptManager.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByLogin(String login);

    @Query("SELECT per FROM Person per " +
            " WHERE per.login LIKE CONCAT('%',:search,'%') ")
    List<Person> findAllByLoginLike(String search);
}
