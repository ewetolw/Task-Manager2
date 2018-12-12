package com.pd.eweltol.taskManager2.service;

import com.pd.eweltol.taskManager2.dto.ProblemPackage;
import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.Task;
import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.model.types.ProblemStatus;
import com.pd.eweltol.taskManager2.model.types.Role;
import com.pd.eweltol.taskManager2.repository.ProblemRepository;
import com.pd.eweltol.taskManager2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {


    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemService problemService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserRepository userRepository;




    public List<Problem> getProblems(){

        String username = getCurrentUserUsername();
        List<Problem> problemList = problemService.findAllProblems(username);
        if(problemList!=null) {
            return problemList;
        }
        else{
            return new ArrayList<>();
        }
    }

    public List<User> getUsers(){

        List<User> userList = userRepository.findAllByRole(Role.WORKER);
        if(userList!=null) {
            return userList;
        }
        else{
            return new ArrayList<>();
        }
    }


    public List<Problem> getProblemById(Long problemId){
        List<Problem> problemList = new ArrayList<>();
        String username = getCurrentUserUsername();
        Problem problem = problemService.findProblemById(username, problemId);
        if(problem!=null){
            problemList.add(problem);
        }
        return problemList;
    }

    public List<Problem> getProblemByClient(String client){
        String username = getCurrentUserUsername();
        List<Problem>  problemList = problemService.findProblemByClient(username, client);
        return problemList;
    }

    public List<Problem> getProblemByStatus(String status){
        String username = getCurrentUserUsername();
        List<Problem> problemList = problemService.findProblemByStatus(username, status);
        return problemList;

    }

    public List<Problem> getProblemByDate(String date) throws ParseException {
        String username = getCurrentUserUsername();
        List<Problem> problemList = problemService.findProblemByDate(username, date);
        return problemList;

    }


    public List<Problem> getProblemWithoutManager(){
        List<Problem> problemList = problemService.problemRepository.findProblemByPrincipalIsNull();
        if(problemList!=null) {
         return problemList;
        }
        return new ArrayList<>();
    }

    public void addProblem(Problem problem){
        String username = getCurrentUserUsername();
        problemService.addProblem(username, problem);
    }


    public void addProblemPackage(ProblemPackage problemPackage){
        String username = getCurrentUserUsername();
        if(problemPackage.getTaskList()!=null){
            problemPackage.getProblem().setTasksList(problemPackage.getTaskList());
        }
        problemService.addProblem(username,problemPackage.getProblem());

    }



    public boolean deleteProblem(Long id){
        String username=getCurrentUserUsername();
        return problemService.deleteProblem(username, id);
    }

    public boolean updateProblem(Problem problem){
        String username = getCurrentUserUsername();
        return problemService.updateProblem(username, problem);
    }

    public boolean changeStatusProblem(Problem problem){
        String username = getCurrentUserUsername();
        return problemService.changeStatusProblem(username, problem);
    }


    public boolean takeOverProblemWithoutManager(Long id){
        String username = getCurrentUserUsername();
        return problemService.takeOverProblemWithoutManager(username, id);

    }

    public boolean addTask(Task task, Long problemId ,Long usernameId){
        String username = getCurrentUserUsername();
        return taskService.addTask(task, problemId, usernameId);
    }

    public boolean updateTaskContent(Task task, Long workerId){
        String username = getCurrentUserUsername();
        return taskService.updateTaskContent(username, task, workerId);
    }

    public boolean deleteTask(Long id){
        String username = getCurrentUserUsername();
        return taskService.deleteTask(username, id);
    }

    public boolean assignTask(Long taskId, Long workerId, Long problemId){
        String username = getCurrentUserUsername();
        return taskService.assignTask(taskId, workerId,problemId, username);

    }



    private static String  getCurrentUserUsername(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User u =(org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return u.getUsername();
    }






}
