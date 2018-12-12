package com.pd.eweltol.taskManager2.model;



import com.pd.eweltol.taskManager2.model.types.ProblemStatus;
import com.pd.eweltol.taskManager2.model.types.TaskStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="PROBLEMS")
public class Problem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<Task> tasksList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User principal;

    @NotNull(message = "content must not be null")
    @Size(min=15, message = "contetn is too short. Write min. 15 characters!")
    @Column(columnDefinition = "TEXT")
    private String content;

    private Date openDate;

    private Date changeStatusDate;

    private String client;

    private ProblemStatus status;



    public Problem() {
    }


    public Problem(User principal, String content, Date openDate, Date changeStatusDate, String client, ProblemStatus status) {
        this.principal = principal;
        this.content = content;
        this.openDate = openDate;
        this.changeStatusDate = changeStatusDate;
        this.client = client;
        this.status = status;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public ProblemStatus getStatus() {
        return status;
    }

    public void setStatus(ProblemStatus status) {
        this.status = status;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }

    public Date getChangeStatusDate() {
        return changeStatusDate;
    }

    public void setChangeStatusDate(Date changeStatusDate) {
        this.changeStatusDate = changeStatusDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public boolean changeStatus(ProblemStatus problemStatus){

        if(problemStatus.getNr()>=this.status.getNr()){
            this.setStatus(problemStatus);
            return true;
        }
        return false;
    }

    public void updateProblemfield(Problem updateData){

        this.setContent(updateData.getContent());
        this.setClient(updateData.getClient());

    }



}
