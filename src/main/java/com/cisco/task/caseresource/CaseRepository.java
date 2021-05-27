package com.cisco.task.caseresource;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Integer> {


    List<Case> getByUserIdAndCaseId(Integer userId, Case.Status status);

    List<Case> getByUserId(Integer userId);

    List<Case> getByStatus(Case.Status status);

}
