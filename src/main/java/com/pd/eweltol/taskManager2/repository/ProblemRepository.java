package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

}
