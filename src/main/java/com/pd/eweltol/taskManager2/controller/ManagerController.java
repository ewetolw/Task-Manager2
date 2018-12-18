package com.pd.eweltol.taskManager2.controller;

import com.pd.eweltol.taskManager2.dto.ProblemPackage;
import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.Task;
import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
    ManagerService managerService;

    @GetMapping("/problems")
    @ResponseBody
    public ResponseEntity getAllProblems(){
        List<Problem> problems= managerService.getProblems();
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/workers")
    @ResponseBody
    public ResponseEntity getAllWorkers(){
        List<User> usersList= managerService.getUsers();
        return ResponseEntity.ok(usersList);
    }


    @GetMapping("/problem/id/{id}")
    @ResponseBody
    public ResponseEntity getProblemById(@PathVariable(value="id") String id){
        Long ID;
        try {
             ID = Long.parseLong(id);
        }catch (Exception e){
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        List<Problem> problems = managerService.getProblemById(ID);
        return ResponseEntity.ok().body(problems);
    }

    @GetMapping("/problem/client/{client}")
    @ResponseBody
    public ResponseEntity getProblemByClient(@PathVariable(value="client") String client){
        List<Problem> problems = managerService.getProblemByClient(client);
        return ResponseEntity.ok().body(problems);
    }


    @GetMapping("/problem/date/{date}")
    @ResponseBody
    public ResponseEntity getProblemByDate(@PathVariable(value="date") String date){
       List<Problem> problemList = new ArrayList<>();
        try {
            problemList = managerService.getProblemByDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(problemList);
    }


    @GetMapping("/problem/status/{status}")
    @ResponseBody
    public ResponseEntity getProblemByStatus(@PathVariable(value="status") String status){
       return ResponseEntity.ok().body(managerService.getProblemByStatus(status));
    }


    @GetMapping("/problem/noManager")
    @ResponseBody
    public ResponseEntity getProblemsWithoutManager(){
        return ResponseEntity.ok().body(managerService.getProblemWithoutManager());
    }


    @PostMapping("/problem")
    @ResponseBody
    public ResponseEntity addProblem(@Valid @RequestBody Problem problem){
        try {
            managerService.addProblem(problem);
        }catch (Exception e){
            return ResponseEntity.ok().body(e);
        }
        return ResponseEntity.ok().body("{\"successful\" : \"added problem\"}");
    }

    @DeleteMapping("/problem/{id}")
    @ResponseBody
    public ResponseEntity deleteProblem(@PathVariable(value="id") Long id){
        boolean deleted;
        try {
            deleted = managerService.deleteProblem(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"error\"\" : \"can not delete problem\"}");
        }
            if(deleted == true) {
                return ResponseEntity.ok().body("{\"successful\" : \"deleted problem\"}");
            }else {
                return ResponseEntity.badRequest().body("{\"error\" : \"can not delete problem\"}");
            }
    }



    @PutMapping("/problem")
    @ResponseBody
    public ResponseEntity updateProblem(@Valid @RequestBody Problem problem){
        boolean updated;
        try {
            updated = managerService.updateProblem(problem);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update problem. Internal Error\"}");
        }

        if(updated == true) {
            return ResponseEntity.ok().body("{\"successful\" : \"updated problem\"}");
        }else {
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update problem\"}");
        }

    }



    @PostMapping("/problemStatus")
    @ResponseBody
    public ResponseEntity changeStatusProblem(@RequestBody Problem status){
        boolean updateStatus;
        try {
            updateStatus = managerService.changeStatusProblem(status);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update problem status. Internal Error\"}");
        }
        if(updateStatus==true) {
            return ResponseEntity.ok().body("{\"successful\" : \"updated problem status\"}");
        }else{
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update problem status.\"}");
        }
    }


    @PostMapping("/problemWithoutManager/{id}")
    @ResponseBody
    public ResponseEntity takeOverProblemWithoutManager(@PathVariable(value="id") Long id){
        boolean tookOverProblem;
        try {
            tookOverProblem = managerService.takeOverProblemWithoutManager(id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("{\"error\" : \"can not take over problem status. Internal Error\"}");
        }

        if(tookOverProblem==true) {
            return ResponseEntity.ok().body("{\"successful\" : \"took over problem\"}");
        }else{
            return ResponseEntity.badRequest().body("{\"error\" : \"can not took over problem.\"}");
        }
    }


    @PostMapping("/task/{problemid}/{userid}")
    @ResponseBody
    public ResponseEntity addTask(@RequestBody Task task ,@PathVariable(value="problemid") Long problemid, @PathVariable(value="userid") Long userid){
       boolean addedTask;

       try {
                 addedTask = managerService.addTask(task, problemid, userid);
            }catch (Exception e){
                return ResponseEntity.badRequest().body("{\"error\" : \"can not add task.\"}");
            }

        if(addedTask==true) {
            return ResponseEntity.ok().body("{\"successful\" : \"added task\"}");
        }else{
            return ResponseEntity.badRequest().body("{\"error\" : \"can not add task.\"}");
        }


    }

    @PutMapping("/taskContent/{workerId}")
    @ResponseBody
    public ResponseEntity updateTaskContent(@RequestBody Task task, @PathVariable(value="workerId") Long workerId){
        boolean updatedTask;
        try {
            updatedTask = managerService.updateTaskContent(task, workerId);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update task.\"}");
        }
        if(updatedTask==true) {
            return ResponseEntity.ok().body("{\"successful\" : \"updated task\"}");
        }else{
            return ResponseEntity.badRequest().body("{\"error\" : \"can not update task.\"}");
        }
    }



    @DeleteMapping("/task/{id}")
    @ResponseBody
    public ResponseEntity deleteTask(@PathVariable(value="id") Long id){
        boolean deletedtask;
        try {
           deletedtask =  managerService.deleteTask(id);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("{\"error\" : \"can not delete task.\"}");
        }
        if(deletedtask==true) {
            return ResponseEntity.ok().body("{\"successful\" : \"deleted task\"}");
        }else{
            return ResponseEntity.badRequest().body("{\"error\" : \"can not delete task.\"}");
        }

    }

    @PutMapping("/task/{taskId}/{workerId}/{problemId}")
    public ResponseEntity assignTask(@PathVariable(value="taskId") Long taskId,
                                     @PathVariable(value="workerId") Long workerId,
                                     @PathVariable(value="problemId") Long problemId){
        boolean assignedTask = managerService.assignTask(taskId,workerId,problemId);
        return ResponseEntity.ok().body(assignedTask);
    }


}
