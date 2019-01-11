package com.pd.eweltol.taskManager2.service;


import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.Task;
import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.model.types.ProblemStatus;
import com.pd.eweltol.taskManager2.model.types.Role;
import com.pd.eweltol.taskManager2.model.types.TaskStatus;
import com.pd.eweltol.taskManager2.repository.ProblemRepository;
import com.pd.eweltol.taskManager2.repository.TaskRepository;
import com.pd.eweltol.taskManager2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;

    public boolean addTask(Task task, Long problemId, Long userId){

        Optional<User> worker = userRepository.findById(userId);
        Optional<Problem> problem = problemRepository.findById(problemId);
        if(worker.isPresent() && problem.isPresent()){
            if(worker.get().getRole().equals(Role.WORKER)){
                task.setContractorId(worker.get());
                task.setOpenDate(new Date());
                task.setStatus(TaskStatus.NEW);
                task.setFeedback(null);
                task.setChangeStatusDate(null);
                taskRepository.save(task);
                problem.get().getTasksList().add(task);
                if(problem.get().getStatus().equals(ProblemStatus.NEW)){
                    problem.get().setStatus(ProblemStatus.ATTACHED);
                    problem.get().setChangeStatusDate(new Date());
                }
                try {
                    problemRepository.save(problem.get());
                    return true;
                }catch(Exception e){
                   taskRepository.delete(task);
                   return false;
                }

            }
        }

        return false;
    }


    public boolean updateTaskContent(String username, Task task, Long workerId){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);

        for(Problem p : problemList){
            for(Task t: p.getTasksList()){
                if(task.getId().equals(t.getId())){
                    User u = userRepository.findById(workerId).get();
                    t.setContractorId(u);
                    t.setTaskContent(task.getTaskContent());
                    taskRepository.save(t);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteTask(String username, Long id){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);


        for(Problem p : problemList){
            for(Task t: p.getTasksList()){
                if(t.getId().equals(id)){
                    p.getTasksList().remove(t);
                    taskRepository.delete(t);
                    return true;
                }
            }
        }

        return false;
    }


    public boolean assignTask(Long taskId, Long workerId ,Long problemId, String username){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);

        Optional<User> worker = userRepository.findById(workerId);

        if(!worker.get().getRole().equals(Role.WORKER)){
            return false;
        }

            for(Problem p : problemList){
                if(p.getId().equals(problemId)){
                    for(Task t: p.getTasksList()){
                        if(t.getId().equals(taskId)){
                            if(t.getContractorId()==null){
                                t.setContractorId(worker.get());
                               // p.setTasksList(p.getTasksList());

                                taskRepository.save(t);

                                return true;
                            }
                            return false;
                        }

                    }
                }
            }

        return false;
    }


    public List<Task> getMyTasks(String username) {
        User user = userRepository.findByUsername(username);
        List<Task> tasks = taskRepository.findAllByContractor(user);
        Set<Problem> problem = new HashSet<>();
        for (Task t : tasks){
           problem.add( problemRepository.findProblemByTasksListContaining(t));
        }

        return null;
    }

    public boolean changeTaskStatus(String username,Task task){

        User u = userRepository.findByUsername(username);
        List<Task> taskList = taskRepository.findAllByContractor(u);

        for(Task t : taskList){
            if(t.getId().equals(task.getId())){
               if(t.changeStatus(task.getStatus())){
                   t.setChangeStatusDate(new Date());
                   taskRepository.save(t);
                   return true;
               }

            }
        }

        return false;
    }


    public boolean addFeedback(String username, Task task){
        User u = userRepository.findByUsername(username);
        List<Task> taskList = taskRepository.findAllByContractorAndStatusIsNotAndAndStatusIsNot(u,
                TaskStatus.FINISHED, TaskStatus.CANCELED);

        for(Task t : taskList){
            if(t.getId().equals(task.getId())){
                t.setFeedback(task.getFeedback());
                taskRepository.save(t);
                return true;
            }
        }
        return false;



    }



}
