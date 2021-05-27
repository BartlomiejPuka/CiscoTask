package com.cisco.task.caseresource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Integer> {


    List<Case> getByUserIdAndStatus(Integer userId, Case.Status status);

    List<Case> getByUserId(Integer userId);

    List<Case> getByStatus(Case.Status status);

    @Query("select c.status from Case c where c.caseId = :caseId ")
    Case.Status getStatusByCaseId(Integer caseId);

}
