package com.cmput301project.colin.sizebook;

import java.util.Date;
/**
 * Created by Colin on 2/4/2017.
 */

public class customerRecord {
    private String date;
    private String name;
    private String neckInches;
    private String bustInches;
    private String chestInches;
    private String waistInches;
    private String hipInches;
    private String inseamInches;
    private String comment;

    public customerRecord(String name){
        this.name = name;
        this.date = new Date().toString();
        this.neckInches = "N/A";
        this.bustInches = "N/A";
        this.chestInches = "N/A";
        this.waistInches = "N/A";
        this.hipInches = "N/A";
        this.inseamInches = "N/A";
        this.comment = "Enter Comment";
    }

    public customerRecord(Date date, String name){
        this.name = name;
        this.date = date.toString();
        this.neckInches = "N/A";
        this.bustInches = "N/A";
        this.chestInches = "N/A";
        this.waistInches = "N/A";
        this.hipInches = "N/A";
        this.inseamInches = "N/A";
        this.comment = "Enter Comment";
    }
    public void setRecord(int dataIndex, String newValue){
        if(dataIndex == 0){
            this.setName(newValue);
        }
        else if(dataIndex == 1){
            this.setDate(newValue);
        }
        else if(dataIndex == 2){
            this.setNeckInches(newValue);
        }
        else if(dataIndex == 3){
            this.setBustInches(newValue);
        }
        else if(dataIndex == 4){
            this.setChestInches(newValue);
        }
        else if(dataIndex == 5){
            this.setWaistInches(newValue);
        }
        else if(dataIndex == 6){
            this.setHipInches(newValue);
        }
        else if(dataIndex == 7){
            this.setInseamInches(newValue);
        }
        else if(dataIndex == 8){
            this.setComment(newValue);
        }
    }

    public String getRecord(int dataIndex){
        if(dataIndex == 0){
            return this.getName();
        }
        else if(dataIndex == 1){
            return "Date Entered " + this.getDate().toString();
        }
        else if(dataIndex == 2){
            if (this.getNeckInches() == "N/A"){
                return "Please Enter Neck Measurement";
            }else{
                return "Neck: " + this.getNeckInches();
            }
        }
        else if(dataIndex == 3){
            if (this.getBustInches() == "N/A"){
                return "Please Enter Bust Measurement";
            }else{
                return "Bust: " + this.getBustInches();
            }
        }
        else if(dataIndex == 4){
            if (this.getChestInches() == "N/A"){
                return "Please Enter Chest Measurement";
            }else{
                return "Chest: " + this.getChestInches();
            }
        }
        else if(dataIndex == 5){
            if (this.getWaistInches() == "N/A"){
                return "Please Enter Waist Measurement";
            }else{
                return "Waist: " + this.getWaistInches();
            }
        }
        else if(dataIndex == 6){
            if (this.getHipInches() == "N/A"){
                return "Please Enter Hip Measurement";
            }else{
                return "Hip: " + this.getHipInches();
            }
        }
        else if(dataIndex == 7){
            if (this.getInseamInches() == "N/A"){
                return "Please Enter Inseam Measurement";
            }else{
                return "Inseam: " + this.getInseamInches();
            }
        }
        else if(dataIndex == 8){
            return this.getComment();
        }
        else{
            return "No Data Found";
        }

    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeckInches() {
        return neckInches;
    }

    public void setNeckInches(String neckInches) {
        this.neckInches = neckInches;
    }

    public String getBustInches() {
        return bustInches;
    }

    public void setBustInches(String bustInches) {
        this.bustInches = bustInches;
    }

    public String getChestInches() {
        return chestInches;
    }

    public void setChestInches(String chestInches) {
        this.chestInches = chestInches;
    }

    public String getWaistInches() {
        return waistInches;
    }

    public void setWaistInches(String waistInches) {
        this.waistInches = waistInches;
    }

    public String getHipInches() {
        return hipInches;
    }

    public void setHipInches(String hipInches) {
        this.hipInches = hipInches;
    }

    public String getInseamInches() {
        return inseamInches;
    }

    public void setInseamInches(String inseamInches) {
        this.inseamInches = inseamInches;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
