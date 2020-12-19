package com.deptManager.deptManager.repositories;

import com.deptManager.deptManager.model.CommonGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommonGoalRepository extends JpaRepository<CommonGoal, UUID> {
}
