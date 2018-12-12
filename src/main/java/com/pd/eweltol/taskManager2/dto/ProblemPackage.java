package com.pd.eweltol.taskManager2.dto;

import com.pd.eweltol.taskManager2.model.Problem;
import com.pd.eweltol.taskManager2.model.Task;

import java.util.ArrayList;

public class ProblemPackage {
    private Problem problem;
    private ArrayList<Task> taskList;

    public ProblemPackage(){}

    public ProblemPackage(Problem problem, ArrayList<Task> taskList) {
        this.problem = problem;
        this.taskList = taskList;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
