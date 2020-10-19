package com.example.adsmobileapp.rest.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Job implements Serializable {
    @SerializedName("job_title")
    private String title;
    @SerializedName("job_type")
    private String type;
    @SerializedName("job_description")
    private String description;
    @SerializedName("requred_skills")
    private String requredSkills;
    @SerializedName("job_offer")
    private String offer;
    private String location;
    @SerializedName("company_name")
    private String companyName;
    private String date;
    @SerializedName("requred_level")
    private String requredLevel;
    @SerializedName("technologies_requred")
    private String technologiesRequred;
    @SerializedName("company_email")
    private String companyEmail;
    @SerializedName("is_promoted")
    private Boolean isPromoted = false;
    @SerializedName("bonus_skills")
    private String bonusSkills;
    private User user;

    public Job() {
    }

    public Job(String title, String description, String requredSkills, String offer, String location, String companyName,
               String date, String requredLevel, String technologiesRequred, String companyEmail,
               User user, String bonusSkills) {
        this.title = title;
        this.description = description;
        this.requredSkills = requredSkills;
        this.offer = offer;
        this.location = location;
        this.companyName = companyName;
        this.date = date;
        this.requredLevel = requredLevel;
        this.technologiesRequred = technologiesRequred;
        this.companyEmail = companyEmail;
        this.user = user;
        this.bonusSkills = bonusSkills;
    }


    public Boolean isPromoted() {
        return isPromoted;
    }

    public Boolean getPromoted() {
        return isPromoted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPromoted(Boolean promoted) {
        isPromoted = promoted;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }


    public String getTechnologiesRequred() {
        return technologiesRequred;
    }

    public void setTechnologiesRequred(String technologiesRequred) {
        this.technologiesRequred = technologiesRequred;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequredSkills() {
        return requredSkills;
    }

    public void setRequredSkills(String requredSkills) {
        this.requredSkills = requredSkills;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequredLevel() {
        return requredLevel;
    }

    public void setRequredLevel(String requredLevel) {
        this.requredLevel = requredLevel;
    }

    public String getBonusSkills() {
        return bonusSkills;
    }
}
