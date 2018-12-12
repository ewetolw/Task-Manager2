package com.pd.eweltol.taskManager2.model;



import com.pd.eweltol.taskManager2.model.types.ProblemStatus;
import com.pd.eweltol.taskManager2.model.types.TaskStatus;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="TASKS")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User contractor;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private String taskContent;

    private TaskStatus status;

    private Date openDate;

    private Date changeStatusDate;



    public Task(){

    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getContractorId() {
        return contractor;
    }

    public void setContractorId(User contractor) {
        this.contractor = contractor;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getChangeStatusDate() {
        return changeStatusDate;
    }

    public void setChangeStatusDate(Date changeStatusDate) {
        this.changeStatusDate = changeStatusDate;
    }

    public boolean changeStatus(TaskStatus taskStatus){

        if(taskStatus.getNr()>=this.status.getNr() && (this.getStatus()!=TaskStatus.CANCELED ||this.getStatus()!=TaskStatus.FINISHED) ){
            this.setStatus(taskStatus);
            return true;
        }
        return false;
    }

}
