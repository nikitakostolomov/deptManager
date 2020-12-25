package com.deptManager.deptManager.repositories;

import com.deptManager.deptManager.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeptRepository extends JpaRepository<Dept, UUID> {

    @Modifying
    @Query("Delete FROM Dept dept " +
            " WHERE dept.receiver.id=?1 or dept.payer.id=?1")
    void deleteAllByPayerIdOrReceiverId(UUID payerId);
}
