package com.cmput301project.colin.sizebook;

import java.util.Date;
/**
 * Created by Colin on 2/4/2017.
 */

public class customerRecord {
    private Date date;
    private String name;
    private int neckInches;
    private int bustInches;
    private int chestInches;
    private int waistInches;
    private int hipInches;
    private int inseamInches;
    private String comment;

    public customerRecord(String name){
        this.name = name;
        this.date = new Date();
        this.neckInches = -1;
        this.bustInches = -1;
        this.chestInches = -1;
        this.waistInches = -1;
        this.hipInches = -1;
        this.inseamInches = -1;
        this.comment = "Enter Comment";
    }

    public customerRecord(Date date, String name){
        this.name = name;
        this.date = date;
        this.neckInches = -1;
        this.bustInches = -1;
        this.chestInches = -1;
        this.waistInches = -1;
        this.hipInches = -1;
        this.inseamInches = -1;
        this.comment = "Enter Comment";
    }

    public String getRecord(int dataIndex){
        if(dataIndex == 0){
            return this.getName();
        }
        else if(dataIndex == 1){
            return "Date Entered " + this.getDate().toString();
        }
        else if(dataIndex == 2){
            if (this.getNeckInches() == -1){
                return "Please Enter Neck Measurement";
            }else{
                return "Neck: " + this.getNeckInches();
            }
        }
        else if(dataIndex == 3){
            if (this.getBustInches() == -1){
                return "Please Enter Bust Measurement";
            }else{
                return "Bust: " + this.getBustInches();
            }
        }
        else if(dataIndex == 3){
            if (this.getChestInches() == -1){
                return "Please Enter Chest Measurement";
            }else{
                return "Chest: " + this.getChestInches();
            }
        }
        else if(dataIndex == 4){
            if (this.getWaistInches() == -1){
                return "Please Enter Waist Measurement";
            }else{
                return "Waist: " + this.getWaistInches();
            }
        }
        else if(dataIndex == 5){
            if (this.getHipInches() == -1){
                return "Please Enter Hip Measurement";
            }else{
                return "Hip: " + this.getHipInches();
            }
        }
        else if(dataIndex == 6){
            if (this.getInseamInches() == -1){
                return "Please Enter Inseam Measurement";
            }else{
                return "Inseam: " + this.getInseamInches();
            }
        }
        else if(dataIndex == 7){
            return this.getComment();
        }
        else{
            return "No Data Found";
        }

    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws NameTooLongException {
        if(name.length() < 60){
            throw new NameTooLongException();
        }
        this.name = name;
    }

    public int getNeckInches() {
        return neckInches;
    }

    public void setNeckInches(int neckInches) {
        this.neckInches = neckInches;
    }

    public int getBustInches() {
        return bustInches;
    }

    public void setBustInches(int bustInches) {
        this.bustInches = bustInches;
    }

    public int getChestInches() {
        return chestInches;
    }

    public void setChestInches(int chestInches) {
        this.chestInches = chestInches;
    }

    public int getWaistInches() {
        return waistInches;
    }

    public void setWaistInches(int waistInches) {
        this.waistInches = waistInches;
    }

    public int getHipInches() {
        return hipInches;
    }

    public void setHipInches(int hipInches) {
        this.hipInches = hipInches;
    }

    public int getInseamInches() {
        return inseamInches;
    }

    public void setInseamInches(int inseamInches) {
        this.inseamInches = inseamInches;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
