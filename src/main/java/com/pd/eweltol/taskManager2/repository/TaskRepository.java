package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.Task;
import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.model.types.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByContractor(User u);
    List<Task> findAllByContractorAndStatusIsNotAndAndStatusIsNot(User u, TaskStatus taskStatus1, TaskStatus taskStatus2);
}
