package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
