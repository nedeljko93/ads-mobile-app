package com.example.adsmobileapp;

import com.example.adsmobileapp.rest.model.User;

public class LocalData {
    private User loggedUser;
    private boolean isPostingJob;

    public boolean isPostingJob() {
        return isPostingJob;
    }

    public void setPostingJob(boolean postingJob) {
        isPostingJob = postingJob;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
