package com.deptManager.deptManager.repositories;

import com.deptManager.deptManager.model.GroupPersonLink;
import com.deptManager.deptManager.model.compositeKeys.GroupPersonLinkKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPersonLinkRepository extends JpaRepository<GroupPersonLink, GroupPersonLinkKey> {
}
