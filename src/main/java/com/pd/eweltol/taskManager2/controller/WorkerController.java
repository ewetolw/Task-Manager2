package com.pd.eweltol.taskManager2.controller;

import com.pd.eweltol.taskManager2.model.Task;
import com.pd.eweltol.taskManager2.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;


    @GetMapping("/getMyTasks")
    @ResponseBody
    public ResponseEntity getMyTasks(){
        return ResponseEntity.ok().body(workerService.getMyTasks());
    }


    @PutMapping("/changeStatusTask")
    @ResponseBody
    public ResponseEntity changeStatusTask(@RequestBody Task task){
        boolean changedTaskStatus;
        try {
        changedTaskStatus =workerService.changeTaskStatus(task);
        }catch (Exception e){
           return ResponseEntity.badRequest().body("\"error\" : \"can not change task status. Internal Error.\"");
        }

        if(changedTaskStatus==true) {
            return ResponseEntity.ok().body("\"successful\" : \"changed task status\"");
        }else{
            return ResponseEntity.badRequest().body("\"error\" : \"can not delete task.\"");
        }

    }


    @PutMapping("/addFeedback")
    @ResponseBody
    public ResponseEntity addFeedbackTask(@RequestBody Task task){
     boolean  addedFeedback;

        try {
           addedFeedback = workerService.addFeedback(task);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("\"error\" : \"can not add feedback. Internal Error.\"");
        }

        if(addedFeedback==true) {
            return ResponseEntity.ok().body("\"successful\" : \"changed task feedback\"");
        }else{
            return ResponseEntity.badRequest().body("\"error\" : \"can not add feedback.\"");
        }

    }



}
