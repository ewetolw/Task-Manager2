package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findProblemByPrincipal(User user);
    List<Problem> findProblemByPrincipalIsNull();
}
