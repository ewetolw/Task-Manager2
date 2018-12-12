package com.pd.eweltol.taskManager2.service;


import com.pd.eweltol.taskManager2.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerService {

    @Autowired
    TaskService taskService;


    public List<Task> getMyTasks(){
        String username = getCurrentUserUsername();
        List<Task> taskList = taskService.getMyTasks(username);
        if(taskList==null) {
            return taskList;
        }else{
            return new ArrayList<>();
        }
    }

    public boolean changeTaskStatus(Task t){
        String username = getCurrentUserUsername();
        return taskService.changeTaskStatus(username, t);
    }

    public boolean addFeedback(Task t){
        String username = getCurrentUserUsername();
        return taskService.addFeedback(username, t);
    }



    private static String  getCurrentUserUsername(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User u =(org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return u.getUsername();
    }

}
