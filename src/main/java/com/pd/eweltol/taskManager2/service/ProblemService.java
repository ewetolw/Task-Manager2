package com.pd.eweltol.taskManager2.service;



import com.pd.eweltol.taskManager2.dto.ProblemPackage;
import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.model.types.ProblemStatus;
import com.pd.eweltol.taskManager2.repository.ProblemRepository;
import com.pd.eweltol.taskManager2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProblemService{

    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    UserRepository userRepository;

    public List<Problem> findAllProblems(String username){

        User user = userRepository.findByUsername(username);
        return problemRepository.findProblemByPrincipal(user);
    }


    public Problem findProblemById(String username, Long id){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);
        for(Problem p : problemList){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public List<Problem> findProblemByClient(String username, String client){
        List<Problem> clientsProblems = new ArrayList<>();
        User user = userRepository.findByUsername(username);
        List<Problem> currentUserProblemList = problemRepository.findProblemByPrincipal(user);
        for(Problem p : currentUserProblemList){
            if(p.getClient().equals(client)){
                clientsProblems.add(p);
            }
        }
        return clientsProblems;
    }

    public void addProblem(String username, Problem problem){
        problem.setChangeStatusDate(null);
        problem.setTasksList(null);
        problem.setId(null);
        problem.setStatus(ProblemStatus.NEW);
        problem.setOpenDate(new Date());
        problem.setClient(problem.getClient().toUpperCase());
        problem.setPrincipal(userRepository.findByUsername(username));
        problemRepository.save(problem);
    }


    public void addProblemPackage(String username, ProblemPackage problemPackage){

        Problem problem = problemPackage.getProblem();
        problem.setChangeStatusDate(null);
        problem.setTasksList(null);
        problem.setId(null);
        problem.setStatus(ProblemStatus.NEW);
        problem.setOpenDate(new Date());
        problem.setPrincipal(userRepository.findByUsername(username));
        problemRepository.save(problem);
        problem.getId();
        System.out.println((problem.getId()));

    }




    public List<Problem> findProblemByStatus(String username, String status){
        status = status.toUpperCase();
        ProblemStatus searchRole;
        try {
            searchRole = ProblemStatus.valueOf(status);
        }catch(Exception e){
            return new ArrayList<>();
        }

        List<Problem> problems = new ArrayList<>();
        User user = userRepository.findByUsername(username);
        List<Problem> currentUserProblemList = problemRepository.findProblemByPrincipal(user);
        for(Problem p: currentUserProblemList){
            if(p.getStatus().equals(searchRole)){
                problems.add(p);
            }
        }
        return problems;
    }

    public List<Problem> findProblemByDate(String username, String Date) throws ParseException {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ((DateFormat) formatter).parse(Date);

        List<Problem> problems = new ArrayList<>();
        User user = userRepository.findByUsername(username);
        List<Problem> currentUserProblemList = problemRepository.findProblemByPrincipal(user);
        Date comparedDate;
        for(Problem p : currentUserProblemList){
           comparedDate = zeroTimeDate(p.getOpenDate());
           if(comparedDate.equals(date)){
               problems.add(p);
           }
        }
        return problems;
    }


    public boolean deleteProblem(String username, Long id){
        User user = userRepository.findByUsername(username);
        List<Problem> currentUserProblemList = problemRepository.findProblemByPrincipal(user);

        for(Problem p: currentUserProblemList){
            if(p.getId().equals(id)){
                problemRepository.delete(p);
                return true;
            }
        }
        return false;
    }


    public boolean updateProblem(String username, Problem problem){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);
        for(Problem p : problemList){
            if(problem.getId().equals(p.getId())){
                p.updateProblemfield(problem);
                problemRepository.save(p);
                return true;
            }
        }
        return false;
    }

    public boolean changeStatusProblem(String username, Problem problem){
        User user = userRepository.findByUsername(username);
        List<Problem> problemList = problemRepository.findProblemByPrincipal(user);

        for(Problem p : problemList){
            if(problem.getId().equals(p.getId())){
                if(p.changeStatus(problem.getStatus())) {
                    problemRepository.save(p);
                    return true;
                }
                return false;
            }
        }

        return false;
    }


    public boolean takeOverProblemWithoutManager(String username, Long id){
        User newManager = userRepository.findByUsername(username);

        Optional<Problem> problem = problemRepository.findById(id);
        if(problem.isPresent()){
            User u = problem.get().getPrincipal();
            if(u==null){
                problem.get().setPrincipal(newManager);
                problemRepository.save(problem.get());
                return true;
            }
        }

        return false;
    }


    private static Date zeroTimeDate(Date deleteTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime( deleteTime );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        deleteTime = calendar.getTime();

        return deleteTime;
    }








}
