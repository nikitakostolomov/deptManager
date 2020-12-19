package com.deptManager.deptManager.repositories;

import com.deptManager.deptManager.model.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeptRepository extends JpaRepository<Dept, UUID> {
}
